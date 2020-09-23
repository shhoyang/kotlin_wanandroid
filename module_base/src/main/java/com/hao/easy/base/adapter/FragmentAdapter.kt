package com.hao.easy.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author Yang Shihao
 * @date 2018/11/21
 */
class FragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var data: List<Fragment>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = data.size

    override fun createFragment(position: Int) = data[position]
}

