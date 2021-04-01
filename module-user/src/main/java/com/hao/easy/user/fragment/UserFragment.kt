package com.hao.easy.user.fragment

import com.hao.easy.base.Config
import com.hao.easy.base.user.User
import com.hao.easy.base.user.UserDb
import com.hao.easy.user.R
import com.hao.easy.user.Router
import com.hao.easy.user.activity.AboutActivity
import com.hao.easy.user.activity.LoginActivity
import com.hao.easy.user.databinding.UserFragmentUserBinding
import com.hao.easy.user.databinding.UserNavigationHeaderBinding
import com.hao.easy.user.viewmodel.UserViewModel
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.extensions.loadCircle
import com.hao.library.ui.BaseFragment
import com.tencent.bugly.beta.Beta

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
class UserFragment : BaseFragment<UserFragmentUserBinding, UserViewModel>() {

    private lateinit var navigationHeaderBinding: UserNavigationHeaderBinding

    override fun initView() {
        viewBinding {
            navigationHeaderBinding =
                UserNavigationHeaderBinding.inflate(layoutInflater, root, true)
            navigationHeaderBinding.ivAvatar.loadCircle(R.mipmap.user_avatar)
            leftNavigationView.setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_favorite -> {
                        if (Config.isLogin) {
                            Router.startFavoritesActivity()
                        } else {
                            startLogin()
                        }
                    }
                    R.id.menu_clear_cache -> toast(R.string.user_clear_completed)
                    R.id.menu_upgrade -> Beta.checkUpgrade()
                    R.id.menu_about -> toA(AboutActivity::class.java)
                    R.id.menu_logout -> logout()
                }
                true
            }
        }
    }

    override fun initData() {
        UserDb.instance().userDao().lastUser().observe(this) {
            setUser(it)
        }
        viewModel {
            logoutLiveData.observe(this@UserFragment) {
                startLogin()
            }
        }
    }

    private fun logout() {
        Config.logout()
        vm?.logout()
    }

    private fun setUser(user: User?) {
        if (user == null) {
            navigationHeaderBinding.tvUsername.text = getString(R.string.user_not_login)
            navigationHeaderBinding.ivAvatar.setOnClickListener {
                startLogin()
            }
            navigationHeaderBinding.tvUsername.setOnClickListener {
                startLogin()
            }
            viewBinding { leftNavigationView.menu.findItem(R.id.menu_logout).isVisible = false }

        } else {
            navigationHeaderBinding.tvUsername.text = user.username
            navigationHeaderBinding.ivAvatar.setOnClickListener(null)
            navigationHeaderBinding.tvUsername.setOnClickListener(null)
            viewBinding { leftNavigationView.menu.findItem(R.id.menu_logout).isVisible = true }
        }
    }

    private fun startLogin() {
        toA(LoginActivity::class.java)
    }
}