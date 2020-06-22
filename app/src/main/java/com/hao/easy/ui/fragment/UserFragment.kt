package com.hao.easy.ui.fragment

import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hao.easy.Config
import com.hao.easy.base.extensions.loadCircle
import com.hao.easy.base.extensions.snack
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.R
import com.hao.easy.ui.activity.AboutActivity
import com.hao.easy.ui.activity.FavActivity
import com.hao.easy.ui.activity.KnowledgeActivity
import com.hao.easy.ui.activity.LoginActivity
import com.hao.easy.user.User
import com.hao.easy.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.user_fragment_user.*


/**
 * @author Yang Shihao
 * @date 2018/11/28
 */
class UserFragment : BaseFragment() {

    private val viewModel: UserViewModel by viewModels()

    private lateinit var tvUsername: TextView

    override fun getLayoutId() = R.layout.user_fragment_user

    override fun initView() {

        tvUsername = leftNavigationView.getHeaderView(0).findViewById(R.id.tvUsername)
        leftNavigationView.getHeaderView(0).findViewById<ImageView>(R.id.ivAvatar)
            .loadCircle(R.mipmap.ic_launcher_round)
        leftNavigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_knowledge -> startActivity(KnowledgeActivity::class.java)
                R.id.menu_fav -> {
                    if (Config.isLogin) {
                        startActivity(FavActivity::class.java)
                    } else {
                        startLogin()
                    }
                }
                R.id.menu_clear -> leftNavigationView.snack("清理完成")
                R.id.menu_about -> startActivity(Intent(context, AboutActivity::class.java))
                R.id.menu_logout -> {
                    Config.logout()
                    viewModel.logout()
                }
            }
            true
        }
    }

    override fun initData() {
        lifecycle.addObserver(viewModel)
        viewModel.loginLiveData.observe(this, Observer {
            setLogin(it)
        })
        viewModel.logoutLiveData.observe(this, Observer {
            if (it == null) {
                startLogin()
            }
        })
    }

    private fun setLogin(user: User?) {
        if (user == null) {
            tvUsername.text = "未登录"
            tvUsername.setOnClickListener {
                startLogin()
            }
            leftNavigationView.menu.findItem(R.id.menu_logout).isVisible = false

        } else {
            tvUsername.text = user.username
            tvUsername.setOnClickListener(null)
            leftNavigationView.menu.findItem(R.id.menu_logout).isVisible = true
        }
    }

    private fun startLogin() {
        context?.apply { LoginActivity.start(this) }
    }
}