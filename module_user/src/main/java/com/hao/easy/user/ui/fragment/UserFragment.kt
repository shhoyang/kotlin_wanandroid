package com.hao.easy.user.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.widget.ImageView
import android.widget.TextView
import com.hao.easy.base.Config
import com.hao.easy.base.extensions.loadCircle
import com.hao.easy.base.extensions.snack
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.base.user.User
import com.hao.easy.user.R
import com.hao.easy.user.Router
import com.hao.easy.user.ui.activity.AboutActivity
import com.hao.easy.user.ui.activity.LoginActivity
import com.hao.easy.user.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.user_fragment_user.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity


/**
 * @author Yang Shihao
 * @date 2018/11/28
 */
class UserFragment : BaseFragment() {

    private lateinit var viewModel: UserViewModel

    private lateinit var tvUsername: TextView

    override fun getLayoutId() = R.layout.user_fragment_user

    override fun initView() {

        tvUsername = leftNavigationView.getHeaderView(0).find(R.id.tvUsername)
        leftNavigationView.getHeaderView(0).find<ImageView>(R.id.ivAvatar)
            .loadCircle(R.mipmap.ic_launcher_round)
        leftNavigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_knowledge -> Router.startKnowledgeActivity()
                R.id.menu_fav -> {
                    if (Config.isLogin) {
                        Router.startFavActivity()
                    } else {
                        startActivity<LoginActivity>()
                    }
                }
                R.id.menu_clear -> leftNavigationView.snack("清理完成")
                R.id.menu_about -> startActivity<AboutActivity>()
                R.id.menu_logout -> {
                    Config.logout()
                    viewModel.logout()
                }
            }
            true
        }
    }

    override fun initData() {
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        lifecycle.addObserver(viewModel)
        viewModel.loginLiveData.observe(this, Observer {
            setLogin(it)
        })
        viewModel.logoutLiveData.observe(this, Observer {
            if (it == null) {
                startActivity<LoginActivity>()
            }
        })
    }

    private fun setLogin(user: User?) {
        if (user == null) {
            tvUsername.text = "未登录"
            tvUsername.setOnClickListener {
                startActivity<LoginActivity>()
            }
            leftNavigationView.menu.findItem(R.id.menu_logout).isVisible = false

        } else {
            tvUsername.text = user.username
            tvUsername.setOnClickListener(null)
            leftNavigationView.menu.findItem(R.id.menu_logout).isVisible = true
        }
    }
}