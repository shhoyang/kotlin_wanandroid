package com.hao.easy.kotlin

import com.hao.easy.base.ui.ContainerActivity
import com.hao.easy.kotlin.ui.fragment.KotlinFragment

/**
 * @author Yang Shihao
 * @date 2018/11/26
 */
class MainActivity : ContainerActivity() {

    override fun showToolbar() = false

    override fun getFragment() = KotlinFragment()
}