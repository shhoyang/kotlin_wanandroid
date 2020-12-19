package com.hao.easy.activity

import android.content.Intent
import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.R
import com.hao.easy.base.adapter.FragmentAdapter
import com.hao.easy.base.adapter.FragmentCreator
import com.hao.easy.base.common.AppManager
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.base.ui.UIParams
import com.hao.easy.base.view.dialog.ConfirmDialog
import com.hao.easy.base.view.dialog.ConfirmDialogListener
import com.hao.easy.databinding.AppActivityMainBinding
import com.hao.easy.user.ui.fragment.UserFragment
import com.hao.easy.wan.ui.fragment.KnowledgeFragment
import com.hao.easy.wan.ui.fragment.ProjectFragment
import com.hao.easy.wan.ui.fragment.SearchFragment
import com.hao.easy.wan.ui.fragment.WechatFragment
import com.hao.easy.wan.viewmodel.UpgradeViewModel
import com.tencent.bugly.beta.UpgradeInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : BaseActivity<AppActivityMainBinding, UpgradeViewModel>() {

    private var backPressedTime by Delegates.observable(0L) { _, old, new ->
        if (new - old < 2000) {
            AppManager.instance().exit()
        } else {
            toast("再按返回键退出")
        }
    }

    override fun prepare(uiParams: UIParams, intent: Intent?) {
        uiParams.isTransparentStatusBar = true
    }

    override fun getVB() = AppActivityMainBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(UpgradeViewModel::class.java)

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
            supportFragmentManager.beginTransaction()
                .add(R.id.leftNavigationView, UserFragment())
                .commit()
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                viewPager.setCurrentItem(
                    when (item.itemId) {
                        R.id.tab_project -> 1
                        R.id.tab_knowledge -> 2
                        R.id.tab_search -> 3
                        else -> 0
                    }, false
                )

                true
            }
        }
    }

    override fun initData() {
        viewModel {
            lifecycle.addObserver(this)
            upgradeLiveData.observe(this@MainActivity, Observer {
                showUpgradeDialog(it)
            })
        }
    }

    private fun showUpgradeDialog(upgradeInfo: UpgradeInfo) {
        val message = """
            版本：${upgradeInfo.versionName}
            
            更新说明：
            ${upgradeInfo.newFeature}
            """.trimIndent()
        ConfirmDialog.Builder(this@MainActivity)
            .setTitle("发现新版本")
            .setMessage(message)
            .setMessageGravity(Gravity.LEFT)
            .setCancelText("下次再说")
            .setConfirmText("立即更新")
            .setListener(object : ConfirmDialogListener {
                override fun confirm() {
                    viewModel { startDownload() }
                }

                override fun cancel() {
                    viewModel { cancelDownload() }
                }

            }).build().show()
    }

    override fun onBackPressed() {
        viewBinding {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                backPressedTime = System.currentTimeMillis()
            }
        }
    }
}
