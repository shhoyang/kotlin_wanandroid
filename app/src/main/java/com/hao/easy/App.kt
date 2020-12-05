package com.hao.easy

import com.alibaba.android.arouter.launcher.ARouter
import com.hao.easy.base.BaseApplication
import com.hao.easy.base.Config
import com.socks.library.KLog
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import dagger.hilt.android.HiltAndroidApp
import kotlin.concurrent.thread

/**
 * @author Yang Shihao
 * @Date 2020/7/18
 *
 * 优化记录
 *
 * 优化前3799.3、3730.5、3727.0、3810.3
 *
 * 将WelcomeActivity从user放到app，不用ARouter跳转而用sdk的api跳转，ARouter异步初始化。2.2ms
 */

@HiltAndroidApp
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
//        val time = SimpleDateFormat("HH_mm").format(Date())
//        Debug.startMethodTracing("${time}_trace", 1024 * 1024 * 32)
        thread {
            Config.init()
            initARouter()
            initX5()
        }
    }

    private fun initX5() {
        QbSdk.initTbsSettings(
            mapOf(
                TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER to true,
                TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE to true
            )
        )
        val callback = object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
                KLog.d(TAG, "onCoreInitFinished")
            }

            override fun onViewInitFinished(p0: Boolean) {
                KLog.d(TAG, "onViewInitFinished:$p0")
            }
        }
        QbSdk.setDownloadWithoutWifi(true)
        QbSdk.initX5Environment(this, callback)
    }

    private fun initARouter() {
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

    companion object {
        private const val TAG = "--App--"
    }
}