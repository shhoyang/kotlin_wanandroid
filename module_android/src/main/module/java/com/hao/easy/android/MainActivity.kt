package com.hao.easy.android

import com.hao.easy.android.ui.fragment.AndroidFragment
import com.hao.easy.base.ui.ContainerActivity


/**
 * @author Yang Shihao
 * @date 2018/11/26
 */
class MainActivity : ContainerActivity() {

    override fun showToolbar() = false

    override fun getFragment() = AndroidFragment()
}