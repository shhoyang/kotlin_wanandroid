package com.hao.easy.wan.viewmodel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.BaseApplication
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.wan.R
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.UpgradeInfo
import com.tencent.bugly.beta.download.DownloadListener
import com.tencent.bugly.beta.download.DownloadTask
import com.tencent.bugly.beta.upgrade.UpgradeListener
import java.text.NumberFormat

class UpgradeViewModel : BaseViewModel() {

    val upgradeLiveData = MutableLiveData<UpgradeInfo>()

    override fun onCreate() {
        super.onCreate()
        Beta.initDelay = 2 * 1000
        Beta.enableNotification = false
        Beta.upgradeListener = UpgradeListener { _, upgradeInfo, _, _ ->
            if (upgradeInfo != null) {
                upgradeLiveData.value = upgradeInfo
            }
        }
        Beta.init(BaseApplication.instance, true)
    }

    fun startDownload() {
        createNotification()
        Beta.registerDownloadListener(object : DownloadListener {
            override fun onReceive(downloadTask: DownloadTask) {
                downloading(downloadTask.totalLength.toInt(), downloadTask.savedLength.toInt())
            }

            override fun onCompleted(downloadTask: DownloadTask) {
                success()
                Beta.installApk(downloadTask.saveFile)
            }

            override fun onFailed(downloadTask: DownloadTask, code: Int, msg: String?) {
                failed()
            }
        })
        Beta.startDownload()
    }

    fun cancelDownload() {
        Beta.cancelDownload()
    }

    override fun onDestroy() {
        super.onDestroy()
        Beta.unregisterDownloadListener()
    }

    private val notificationManager: NotificationManager by lazy {
        BaseApplication.instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val notificationBuilder: NotificationCompat.Builder by lazy {
        createNotificationBuilder()
    }

    private val numberFormat: NumberFormat by lazy {
        val numberFormat = NumberFormat.getPercentInstance()
        numberFormat.maximumFractionDigits = 0
        numberFormat
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    DOWNLOAD_CHANNEL_ID,
                    "版本更新",
                    NotificationManager.IMPORTANCE_HIGH
                )
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            channel.setShowBadge(true)
            channel.setBypassDnd(true)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            BaseApplication.instance,
            DOWNLOAD_CHANNEL_ID
        ).setWhen(System.currentTimeMillis())
            .setContentTitle("版本更新")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    BaseApplication.instance.resources,
                    R.mipmap.ic_launcher
                )
            )
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(false)
            .setContentText("下载进度0%")
            .setProgress(100, 0, false)
    }

    private fun downloading(max: Int, process: Int) {
        notificationBuilder.setProgress(max, process, false)
        notificationBuilder.setContentText(numberFormat.format(process.toFloat() / max))
        notificationManager!!.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun success() {
        notificationBuilder.setContentText("下载成功")
        notificationManager!!.notify(NOTIFICATION_ID, notificationBuilder.build())
        notificationManager!!.cancel(NOTIFICATION_ID)
    }

    private fun failed() {
        notificationBuilder.setAutoCancel(true)
        notificationManager!!.cancel(NOTIFICATION_ID)
    }

    companion object {
        const val DOWNLOAD_CHANNEL_ID = "download"
        const val NOTIFICATION_ID = 1
    }
}