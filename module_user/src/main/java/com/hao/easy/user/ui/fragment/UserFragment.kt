package com.hao.easy.user.ui.fragment

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.base.Config
import com.hao.easy.base.extensions.loadCircle
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.base.user.User
import com.hao.easy.user.R
import com.hao.easy.user.Router
import com.hao.easy.user.ui.activity.AboutActivity
import com.hao.easy.user.ui.activity.LoginActivity
import com.hao.easy.user.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.user_fragment_user.*

/**
 * @author Yang Shihao
 * @date 2018/11/28
 */
class UserFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    private lateinit var tvUsername: TextView

    override fun getLayoutId() = R.layout.user_fragment_user

    override fun initView() {
        tvUsername = leftNavigationView.getHeaderView(0).findViewById(R.id.tvUsername)
        leftNavigationView.getHeaderView(0).findViewById<ImageView>(R.id.ivAvatar)
            .loadCircle(R.mipmap.user_avatar)
        leftNavigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_fav -> {
                    if (Config.isLogin) {
                        Router.startFavActivity()
                    } else {
                        startLogin()
                    }
                }
                R.id.menu_clear -> toast("清理完成")
                R.id.menu_about -> to(AboutActivity::class.java)
                R.id.menu_logout -> {
                    Config.logout()
                    viewModel.logout()
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        setUser(Config.user)
    }

    override fun initData() {
        viewModel.logoutLiveData.observe(this, Observer {
            startLogin()
        })
    }

    private fun setUser(user: User?) {
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
        to(LoginActivity::class.java)
    }
}