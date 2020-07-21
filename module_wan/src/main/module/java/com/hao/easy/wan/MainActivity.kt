package com.hao.easy.wan

import com.hao.easy.base.adapter.FragmentAdapter
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.wan.ui.fragment.KnowledgeFragment
import com.hao.easy.wan.ui.fragment.ProjectFragment
import com.hao.easy.wan.ui.fragment.SearchFragment
import com.hao.easy.wan.ui.fragment.WechatFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wan_activity_main.*

/**
 * @author Yang Shihao
 * @date 2018/11/26
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun transparentStatusBar() = true
    override fun getLayoutId() = R.layout.wan_activity_main

    override fun initView() {
        val fragments = listOf(
            WechatFragment(),
            ProjectFragment(),
            KnowledgeFragment(),
            SearchFragment()
        )
        viewPager.apply {
            isUserInputEnabled = false
            offscreenPageLimit = 3
            adapter = FragmentAdapter(supportFragmentManager, lifecycle, fragments)
        }
        initBottomNavigation()
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
}