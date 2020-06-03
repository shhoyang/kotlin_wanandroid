package com.hao.easy.base.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ProgressBar
import com.socks.library.KLog
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class ProgressWebView : WebView {

    companion object {
        private const val DURATION = 300L
    }

    var progressBar: ProgressBar? = null
    private var isAnimStart = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        webViewClient = client
        webChromeClient = chromeClient

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
    }

    private val client = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(p0: WebView, p1: String): Boolean {
            p0.loadUrl(p1)
            return true
        }
    }

    private val chromeClient = object : WebChromeClient() {

        override fun onProgressChanged(webView: WebView?, newProgress: Int) {
            super.onProgressChanged(webView, newProgress)
            progressBar?.apply {
                if (newProgress >= 100 && !isAnimStart) {
                    isAnimStart = true
                    startDismissAnim(newProgress)

                } else {
                    startProgressAnim(progress, newProgress)
                }
            }
        }
    }

    private fun startProgressAnim(start: Int, end: Int) {
        val animator = ObjectAnimator.ofInt(progressBar, "progress", start, end)
        animator.duration = DURATION
        animator.start()
    }

    private fun startDismissAnim(progress: Int) {
        val animator = ObjectAnimator.ofFloat(progressBar, "alpha", 1.0F, .0F)
        animator.duration = DURATION
        animator.addUpdateListener {
            val fraction = it.animatedFraction
            val offset = 100 - progress
            setProgress((progress + offset * fraction).toInt())
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                setProgress(0)
                isAnimStart = false
            }
        })
        animator.start()
    }

    private fun setProgress(progress: Int) {
        progressBar?.progress = progress
    }
}
