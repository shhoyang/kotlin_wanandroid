package com.hao.easy

import android.app.IntentService
import android.content.Intent
import com.socks.library.KLog
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk

/**
 * @author Yang Shihao
 * @Date 12/17/20
 *
 * WebView运行在独立的h5进程，为了首次启动WebView进程的时候不造成页面卡顿，要提前启动h5进程初始化x5内核
 * 所以在主进程启动的时候，启动这个Service，也就启动了h5进程，顺便在这个Service初始化x5内核
 */
class InitX5Service : IntentService("x5") {
    override fun onHandleIntent(intent: Intent?) {
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

    companion object {
        private const val TAG = "--Service--"
    }
}