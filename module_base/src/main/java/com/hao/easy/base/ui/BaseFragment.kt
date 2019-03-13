package com.hao.easy.base.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseFragment : Fragment() {

    private lateinit var fragmentRootView: View
    private var isCreated = false
    private var isVisibleToUser = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentRootView = inflater.inflate(getLayoutId(), container, false)
        return fragmentRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onInit()
        initInject()
        initView()
        lodData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            this.isVisibleToUser = true
            lazyLoad()
        }
    }

    open fun onInit() {

    }

    private fun lodData() {
        if (isLazy()) {
            isCreated = true
            lazyLoad()
        } else {
            initData()
        }
    }

    private fun lazyLoad() {
        if (isCreated && isVisibleToUser) {
            initData()
            isCreated = false
        }
    }

    fun <T : View> f(id: Int): T? {
        return fragmentRootView.findViewById(id)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun initInject() {

    }

    open fun initView() {

    }

    open fun initData() {

    }

    open fun isLazy() = false
}