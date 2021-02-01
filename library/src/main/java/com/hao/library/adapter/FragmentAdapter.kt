package com.hao.library.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author Yang Shihao
 */
class FragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var data: List<FragmentCreator>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragments = HashMap<Int, Fragment>()

    override fun getItemCount() = data.size

    override fun createFragment(position: Int): Fragment {
        var f = fragments[position]
        if (f == null) {
            f = data[position].createFragment()
            fragments[position] = f
        }
        return f
    }

    fun getFragment(position: Int): Fragment? {
        return fragments[position]
    }
}

interface FragmentCreator {
    fun createFragment(): Fragment
}

