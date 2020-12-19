package com.hao.easy.user.ui.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.base.Config
import com.hao.easy.base.extensions.loadCircle
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.base.user.User
import com.hao.easy.user.R
import com.hao.easy.user.Router
import com.hao.easy.user.databinding.UserFragmentUserBinding
import com.hao.easy.user.databinding.UserNavigationHeaderBinding
import com.hao.easy.user.ui.activity.AboutActivity
import com.hao.easy.user.ui.activity.LoginActivity
import com.hao.easy.user.viewmodel.UserViewModel
import com.tencent.bugly.beta.Beta

/**
 * @author Yang Shihao
 * @date 2018/11/28
 */
class UserFragment : BaseFragment<UserFragmentUserBinding, UserViewModel>() {

    private lateinit var navigationHeaderBinding: UserNavigationHeaderBinding

    override fun getVB() = UserFragmentUserBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(UserViewModel::class.java)

    override fun initView() {
        viewBinding {
            navigationHeaderBinding =
                UserNavigationHeaderBinding.inflate(layoutInflater, root, true)
            navigationHeaderBinding.ivAvatar.loadCircle(R.mipmap.user_avatar)
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
                    R.id.menu_upgrade -> Beta.checkUpgrade()
                    R.id.menu_about -> toA(AboutActivity::class.java)
                    R.id.menu_logout -> logout()
                }
                true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setUser(Config.user)
    }

    override fun initData() {
        viewModel {
            logoutLiveData.observe(this@UserFragment, Observer {
                startLogin()
            })
        }
    }

    private fun logout() {
        Config.logout()
        viewModel { logout() }
    }

    private fun setUser(user: User?) {
        if (user == null) {
            navigationHeaderBinding.tvUsername.text = "未登录"
            navigationHeaderBinding.tvUsername.setOnClickListener {
                startLogin()
            }
            viewBinding { leftNavigationView.menu.findItem(R.id.menu_logout).isVisible = false }

        } else {
            navigationHeaderBinding.tvUsername.text = user.username
            navigationHeaderBinding.tvUsername.setOnClickListener(null)
            viewBinding { leftNavigationView.menu.findItem(R.id.menu_logout).isVisible = true }
        }
    }

    private fun startLogin() {
        toA(LoginActivity::class.java)
    }
}