package com.hao.easy.activity

import android.content.Intent
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import com.hao.easy.R
import com.hao.easy.base.adapter.FragmentAdapter
import com.hao.easy.base.adapter.FragmentCreator
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.base.ui.UIParams
import com.hao.easy.user.ui.fragment.UserFragment
import com.hao.easy.wan.ui.fragment.KnowledgeFragment
import com.hao.easy.wan.ui.fragment.ProjectFragment
import com.hao.easy.wan.ui.fragment.SearchFragment
import com.hao.easy.wan.ui.fragment.WechatFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.app_activity_main.*
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private var drawerOpened: Boolean = false
    private var backPressedTime by Delegates.observable(0L) { _, old, new ->
        if (new - old < 2000) {
            finish()
        } else {
            toast("再按返回键退出")
        }
    }

    override fun prepare(uiParams: UIParams, intent: Intent?) {
        uiParams.showToolbar = false
    }

    override fun getLayoutId() = R.layout.app_activity_main

    override fun initView() {
        val fragments = listOf(
            object : FragmentCreator {
                override fun createFragment() = WechatFragment()
            },
            object : FragmentCreator {
                override fun createFragment() = ProjectFragment()
            },
            object : FragmentCreator {
                override fun createFragment() = KnowledgeFragment()
            },
            object : FragmentCreator {
                override fun createFragment() = SearchFragment()
            }
        )
        viewPager.apply {
            isUserInputEnabled = false
            offscreenPageLimit = 3
            adapter = FragmentAdapter(supportFragmentManager, lifecycle, fragments)
        }
        initDrawerLayout()
        initLeftNavigation()
        initBottomNavigation()
//        Debug.stopMethodTracing()
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
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            viewPager.setCurrentItem(
                when (item.itemId) {
                    R.id.tab_hot -> 0
                    R.id.tab_project -> 1
                    R.id.tab_knowledge -> 2
                    else -> 3
                }, false
            )

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
}
