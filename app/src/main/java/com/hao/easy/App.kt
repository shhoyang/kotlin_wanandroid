package com.hao.easy

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.hao.easy.base.BaseApplication
import com.hao.easy.base.Config
import com.hao.library.utils.AppUtils
import com.hao.library.utils.CoroutineUtils
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta

/**
 * @author Yang Shihao
 */
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        if (AppUtils.isMainProcess(instance, android.os.Process.myPid())) {
            CoroutineUtils.io {
                Config.init()
                initARouter()
                // 此处很生猛
                startService(Intent(instance, InitX5Service::class.java))
            }
            initBugly()
        }
    }

    private fun initARouter() {
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

    private fun initBugly() {
        Beta.autoInit = false
        Bugly.init(this, "50bf0502bf", true)
    }
}