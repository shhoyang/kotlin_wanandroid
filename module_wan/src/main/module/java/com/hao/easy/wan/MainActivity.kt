package com.hao.easy.wan

import android.content.Intent
import com.hao.easy.base.adapter.FragmentAdapter
import com.hao.easy.base.adapter.FragmentCreator
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.base.ui.UIParams
import com.hao.easy.wan.databinding.WanActivityMainBinding
import com.hao.easy.wan.ui.fragment.KnowledgeFragment
import com.hao.easy.wan.ui.fragment.ProjectFragment
import com.hao.easy.wan.ui.fragment.SearchFragment
import com.hao.easy.wan.ui.fragment.WechatFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Yang Shihao
 * @date 2018/11/26
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<WanActivityMainBinding, Nothing>() {

    override fun prepare(uiParams: UIParams, intent: Intent?) {
        uiParams.isTransparentStatusBar = true
    }

    override fun getVB() = WanActivityMainBinding.inflate(layoutInflater)

    override fun getVM(): Nothing? = null

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
        viewBinding {
            viewPager.apply {
                isUserInputEnabled = false
                offscreenPageLimit = 3
                adapter = FragmentAdapter(supportFragmentManager, lifecycle, fragments)
            }

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

    override fun initData() {
    }
}