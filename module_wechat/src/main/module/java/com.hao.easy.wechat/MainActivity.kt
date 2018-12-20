package com.hao.easy.wechat

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.wechat.ui.fragment.FlutterFragment
import com.hao.easy.wechat.ui.fragment.KotlinFragment
import com.hao.easy.wechat.ui.fragment.ProjectFragment
import com.hao.easy.wechat.ui.fragment.WechatFragment
import kotlinx.android.synthetic.main.wechat_activity_main.*

/**
 * @author Yang Shihao
 * @date 2018/11/26
 */
class MainActivity : BaseActivity() {

    override fun showToolbar() = false

    override fun getLayoutId() = R.layout.wechat_activity_main

    override fun initView() {
        initBottomNavigation()
        viewPager.apply {
            offscreenPageLimit = 3
            adapter = MainViewPagerAdapter(supportFragmentManager)
        }
    }

    private fun initBottomNavigation() {
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            viewPager.currentItem =
                    when (item.itemId) {
                        R.id.tab_wechat -> 0
                        R.id.tab_android -> 1
                        R.id.tab_kotlin -> 2
                        else -> 3
                    }
            true
        }
    }

    inner class MainViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(p0: Int): Fragment {
            return when (p0) {
                0 -> WechatFragment()
                1 -> ProjectFragment()
                2 -> KotlinFragment()
                else -> FlutterFragment()
            }
        }

        override fun getCount() = 4
    }
}