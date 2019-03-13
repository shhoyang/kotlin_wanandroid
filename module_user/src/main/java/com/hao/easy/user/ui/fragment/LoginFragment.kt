package com.hao.easy.user.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.text.TextUtils
import com.hao.easy.base.extensions.addTextChangedListener
import com.hao.easy.base.extensions.hideSoftInput
import com.hao.easy.base.extensions.showError
import com.hao.easy.base.extensions.snack
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.user.R
import com.hao.easy.user.Router
import com.hao.easy.user.ui.activity.LoginActivity
import com.hao.easy.user.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.user_fragment_login.*

/**
 * @author Yang Shihao
 * @date 2018/11/25
 */
class LoginFragment : BaseFragment() {

    companion object {
        private const val TAG = "LoginFragment"
    }

    private lateinit var viewModel: LoginViewModel

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
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.loginLiveData.observe(this, Observer {
            if (it == null) {
                Router.startMainActivity()
                activity?.finish()
            } else {
                editTextUsername.snack(it)
            }
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            activity?.title = "登录"
        }
    }
}
