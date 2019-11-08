package com.hao.easy.activity

import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.view.WindowManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.R
import com.hao.easy.base.extensions.snack
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.user.ui.fragment.UserFragment
import com.hao.easy.wan.ui.fragment.FlutterFragment
import com.hao.easy.wan.ui.fragment.KotlinFragment
import com.hao.easy.wan.ui.fragment.ProjectFragment
import com.hao.easy.wan.ui.fragment.WechatFragment
import kotlinx.android.synthetic.main.app_activity_main.*
import kotlin.properties.Delegates

@Route(path = "/app/MainActivity")
class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var drawerOpened: Boolean = false

    private var backPressedTime by Delegates.observable(0L) { _, old, new ->
        if (new - old < 2000) {
            finish()
        } else {
            drawerLayout?.snack("再按返回鍵退出")
        }
    }

    override fun transparentStatusBar() = true

    override fun getLayoutId() = R.layout.app_activity_main

    override fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            // 使用刘海区域
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }
        initDrawerLayout()
        initLeftNavigation()
        initBottomNavigation()
        viewPager.apply {
            offscreenPageLimit = 3
            adapter = MainViewPagerAdapter(supportFragmentManager)
        }
    }

    private fun initDrawerLayout() {
        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {

            override fun onDrawerClosed(p0: View) {
                drawerOpened = false
            }

            override fun onDrawerOpened(p0: View) {
                drawerOpened = true
            }
        })
    }

    private fun initLeftNavigation() {
        supportFragmentManager.beginTransaction()
            .add(R.id.leftNavigationView, UserFragment())
            .commit()
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

    override fun onBackPressed() {
        if (drawerOpened) {
            drawerLayout.closeDrawers()
        } else {
            backPressedTime = System.currentTimeMillis()
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
