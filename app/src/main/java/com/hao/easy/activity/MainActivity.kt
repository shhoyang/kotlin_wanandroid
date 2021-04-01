package com.hao.easy.activity

import android.content.Intent
import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.hao.easy.R
import com.hao.easy.databinding.AppActivityMainBinding
import com.hao.easy.user.fragment.UserFragment
import com.hao.easy.wan.fragment.KnowledgeFragment
import com.hao.easy.wan.fragment.ProjectFragment
import com.hao.easy.wan.fragment.SearchFragment
import com.hao.easy.wan.fragment.WechatFragment
import com.hao.easy.wan.viewmodel.UpgradeViewModel
import com.hao.library.AppManager
import com.hao.library.adapter.FragmentAdapter
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.ui.BaseActivity
import com.hao.library.ui.FragmentCreator
import com.hao.library.ui.UIParams
import com.hao.library.view.dialog.ConfirmDialog
import com.hao.library.view.dialog.ConfirmDialogListener
import com.tencent.bugly.beta.UpgradeInfo
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : BaseActivity<AppActivityMainBinding, UpgradeViewModel>(),
    WechatFragment.ActivityCallback {

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

    override fun initView() {
        val fragments = listOf(
            object : FragmentCreator {
                override fun createFragment(): Fragment {
                    val fragment = WechatFragment()
                    fragment.setActivityCallback(this@MainActivity)
                    return fragment
                }
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
            upgradeLiveData.observe(this@MainActivity) {
                showUpgradeDialog(it)
            }
        }
    }

    private fun showUpgradeDialog(upgradeInfo: UpgradeInfo) {
        val message = "版本：${upgradeInfo.versionName}\n\n更新说明：\n${upgradeInfo.newFeature}"
        ConfirmDialog.Builder(this@MainActivity)
            .setTitle("发现新版本")
            .setMessage(message)
            .setMessageGravity(Gravity.LEFT)
            .setCancelBtnText("下次再说")
            .setConfirmBtnText("立即更新")
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

    override fun openDraw() {
        viewBinding { drawerLayout.open() }
    }
}
