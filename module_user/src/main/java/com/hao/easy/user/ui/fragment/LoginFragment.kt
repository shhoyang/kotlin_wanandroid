package com.hao.easy.user.ui.fragment

import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.base.extensions.addTextChangedListener
import com.hao.easy.base.extensions.hideSoftInput
import com.hao.easy.base.extensions.showError
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.user.R
import com.hao.easy.user.ui.activity.LoginActivity
import com.hao.easy.user.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.user_fragment_login.*

/**
 * @author Yang Shihao
 * @date 2018/11/25
 */
class LoginFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.user_fragment_login

    override fun initView() {
        editTextUsername.addTextChangedListener(textInputUsername)
        editTextPassword.addTextChangedListener(textInputPassword)
        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            if (TextUtils.isEmpty(username)) {
                textInputUsername.showError("用户名不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                textInputPassword.showError("密码不能为空")
                return@setOnClickListener
            }
            viewModel.login(username, password)
            hideSoftInput()
        }
        textRegister.setOnClickListener {
            activity?.let { act ->
                val a = act as LoginActivity
                a.goRegister()
            }
        }
    }

    override fun initData() {
        viewModel.loginLiveData.observe(this, Observer {
//            Router.startMainActivity()
            activity?.finish()
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            activity?.title = "登录"
        }
    }
}
