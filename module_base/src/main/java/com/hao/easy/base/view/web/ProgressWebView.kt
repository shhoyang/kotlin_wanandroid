package com.hao.easy.base.view.web

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSession
import com.tencent.sonic.sdk.SonicSessionConfig

class ProgressWebView : WebView {

    private var sonicSession: SonicSession? = null
    private var address = "-1"
    private var userSonic = false
    private var pageLoadListener: PageLoadListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        val settings = settings
        settings.setSupportZoom(true)
        settings.setSupportMultipleWindows(true)
        settings.setAppCacheEnabled(true)
        settings.setGeolocationEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.allowFileAccess = true
        settings.javaScriptEnabled = true
        settings.allowContentAccess = true
        settings.databaseEnabled = true
        settings.domStorageEnabled = true
        settings.savePassword = true
        settings.saveFormData = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = true
        settings.loadsImagesAutomatically = true
        settings.displayZoomControls = false
        settings.defaultTextEncodingName = "utf-8"
        webViewClient = client
        webChromeClient = chromeClient
    }

    private val client = object : WebViewClient() {
        override fun onPageFinished(webView: WebView, url: String) {
            super.onPageFinished(webView, url)
            if (userSonic) {
                sonicSession?.sessionClient?.pageFinish(url)
            }
            pageLoadListener?.pageFinished()
        }

        override fun shouldOverrideUrlLoading(p0: WebView, p1: String): Boolean {
            p0.loadUrl(p1)
            return true
        }

        override fun onReceivedError(p0: WebView?, p1: WebResourceRequest, p2: WebResourceError?) {
            super.onReceivedError(p0, p1, p2)
            if (p1.isForMainFrame) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
                pageLoadListener?.pageLoadError()
            }
        }

        override fun shouldInterceptRequest(
            webView: WebView?,
            request: WebResourceRequest
        ): WebResourceResponse? {
            return if (userSonic) {
                shouldInterceptRequest(webView, request.url.toString())
            } else {
                super.shouldInterceptRequest(webView, request)
            }
        }

        override fun shouldInterceptRequest(webView: WebView?, url: String?): WebResourceResponse? {
            return if (userSonic) {
                sonicSession?.sessionClient?.requestResource(url)
                null
            } else {
                super.shouldInterceptRequest(webView, url)
            }

        }
    }

    private val chromeClient = object : WebChromeClient() {
        override fun onProgressChanged(webView: WebView, newProgress: Int) {
            super.onProgressChanged(webView, newProgress)
            pageLoadListener?.apply {
                if (newProgress >= 100) {
                    pageFinished()
                }
                progressChanged(newProgress)
            }
        }
    }

    override fun destroy() {
        if (sonicSession != null) {
            sonicSession!!.destroy()
            sonicSession = null
        }
        pageLoadListener = null
        super.destroy()
    }

    fun setPageLoadListener(pageLoadListener: PageLoadListener?) {
        this.pageLoadListener = pageLoadListener
    }


    fun doLoadUrl(url: String, userSonic: Boolean = false) {
        this.userSonic = userSonic
        if (address == url) {
            reload()
            return
        }
        address = url
        if (!userSonic) {
            loadUrl(address)
            return
        }

        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(
                HostSonicRuntime(context.applicationContext),
                SonicConfig.Builder().build()
            )
        }
        var sonicSessionClient: SonicSessionClientImpl? = null
        sonicSession =
            SonicEngine.getInstance().createSession(address, SonicSessionConfig.Builder().build())
        if (sonicSession != null) {
            sonicSessionClient = SonicSessionClientImpl()
            sonicSession!!.bindClient(sonicSessionClient)
        }

        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(this)
            sonicSessionClient.clientReady()
        } else {
            loadUrl(address)
        }
    }

    interface PageLoadListener {
        fun pageFinished()

        fun pageLoadError()

        fun progressChanged(newProgress: Int)
    }
}
