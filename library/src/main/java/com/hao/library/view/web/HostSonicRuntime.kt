package com.hao.library.view.web

import android.content.Context
import android.text.TextUtils
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.CookieManager
import com.tencent.sonic.sdk.SonicRuntime
import com.tencent.sonic.sdk.SonicSessionClient
import java.io.File
import java.io.InputStream

/**
 * @author Yang Shihao
 */
class HostSonicRuntime(context: Context) : SonicRuntime(context) {

    override fun showToast(text: CharSequence?, duration: Int) {

    }

    override fun log(tag: String?, level: Int, message: String?) {

    }

    override fun getUserAgent(): String {
        return ""
    }

    override fun isNetworkValid(): Boolean {
        return true
    }

    override fun postTaskToThread(task: Runnable?, delayMillis: Long) {
        val thread = Thread(task, "SonicThread")
        thread.start()

    }

    override fun isSonicUrl(url: String?): Boolean {
        return true
    }

    override fun setCookie(url: String?, cookies: MutableList<String>?): Boolean {
        if (!TextUtils.isEmpty(url) && cookies != null && cookies.size > 0) {
            val cookieManager =
                CookieManager.getInstance()
            for (cookie in cookies) {
                cookieManager.setCookie(url, cookie)
            }
            return true
        }
        return false
    }

    override fun getCookie(url: String?): String? {
        val cookieManager = CookieManager.getInstance()
        return cookieManager.getCookie(url)
    }

    override fun createWebResourceResponse(
        mimeType: String?,
        encoding: String?,
        data: InputStream?,
        headers: MutableMap<String, String>?
    ): Any {
        val resourceResponse = WebResourceResponse(mimeType, encoding, data)
        resourceResponse.responseHeaders = headers
        return resourceResponse
    }

    override fun getCurrentUserAccount(): String {
        return ""
    }

    override fun notifyError(client: SonicSessionClient?, url: String?, errorCode: Int) {

    }

    override fun getSonicCacheDir(): File {
        val path: String = context.filesDir.absolutePath + File.separator.toString() + "shihao/"
        val file = File(path.trim())
        if (!file.exists()) {
            file.mkdir()
        }
        return file
    }

}