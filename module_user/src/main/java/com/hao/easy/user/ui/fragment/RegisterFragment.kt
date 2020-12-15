package com.hao.easy.user.ui.fragment

import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.hao.easy.base.extensions.addTextChangedListener
import com.hao.easy.base.extensions.hideSoftInput
import com.hao.easy.base.extensions.showError
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.user.databinding.UserFragmentRegisterBinding
import com.hao.easy.user.viewmodel.RegisterViewModel

/**
 * @author Yang Shihao
 * @date 2018/11/25
 */
class RegisterFragment : BaseFragment<UserFragmentRegisterBinding, RegisterViewModel>() {

    override fun getVB() = UserFragmentRegisterBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(RegisterViewModel::class.java)

    override fun initView() {
        activity?.title = "注册"
        viewBinding {
            editTextUsername.addTextChangedListener(textInputUsername)
            editTextPassword.addTextChangedListener(textInputPassword)
            editTextConfirmPassword.addTextChangedListener(textInputConfirmPassword)
            buttonRegister.setOnClickListener {
                register()
            }
            textLogin.setOnClickListener {
                Navigation.findNavController(root).popBackStack()
            }
        }
    }

    private fun register() {
        viewBinding {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val confirmPassword = editTextConfirmPassword.text.toString().trim()
            if (username.length < 6) {
                textInputUsername.showError("用户名6-16数字、字母")
                return@viewBinding
            }
            if (TextUtils.isEmpty(password)) {
                textInputPassword.showError("密码6-16数字、字母")
                return@viewBinding
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                textInputConfirmPassword.showError("确认密码6-16数字、字母")
                return@viewBinding
            }
            if (password != confirmPassword) {
                textInputConfirmPassword.showError("确认密码与密码不符")
                return@viewBinding
            }
            viewModel {
                register(username, password)
            }
            hideSoftInput()
        }
    }

    override fun initData() {
        viewModel {
            registerLiveData.observe(this@RegisterFragment, Observer {
                parentFragmentManager.popBackStack()
            })
        }
    }
}
