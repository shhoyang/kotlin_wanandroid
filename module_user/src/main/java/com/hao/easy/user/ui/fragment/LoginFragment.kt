package com.hao.easy.user.ui.fragment

import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.hao.easy.base.extensions.addTextChangedListener
import com.hao.easy.base.extensions.hideSoftInput
import com.hao.easy.base.extensions.showError
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.user.R
import com.hao.easy.user.databinding.UserFragmentLoginBinding
import com.hao.easy.user.viewmodel.LoginViewModel

/**
 * @author Yang Shihao
 * @date 2018/11/25
 */
class LoginFragment : BaseFragment<UserFragmentLoginBinding, LoginViewModel>() {

    override fun getVB() = UserFragmentLoginBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(LoginViewModel::class.java)

    override fun initView() {
        activity?.title = "登录"
        viewBinding {
            editTextUsername.addTextChangedListener(textInputUsername)
            editTextPassword.addTextChangedListener(textInputPassword)
            buttonLogin.setOnClickListener {
                login()
            }
            textRegister.setOnClickListener {
                Navigation.findNavController(root).navigate(R.id.toRegisterFragment)
            }
        }
    }

    private fun login() {
        viewBinding {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            if (TextUtils.isEmpty(username)) {
                textInputUsername.showError("用户名不能为空")
                return@viewBinding
            }
            if (TextUtils.isEmpty(password)) {
                textInputPassword.showError("密码不能为空")
                return@viewBinding
            }
            viewModel {
                login(username, password)
            }
            hideSoftInput()
        }
    }

    override fun initData() {
        viewModel {
            loginLiveData.observe(this@LoginFragment, Observer {
                activity?.finish()
            })
        }
    }
}
