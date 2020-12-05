package com.hao.easy.base.ui

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.WindowManager
import android.widget.ProgressBar
import com.hao.easy.base.R
import com.hao.easy.base.view.web.ProgressAnimHelper
import com.hao.easy.base.view.web.ProgressWebView
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity(), ProgressWebView.PageLoadListener {

    private lateinit var webView: ProgressWebView
    private var progressBar: ProgressBar? = null
    private var progressAnimHelper: ProgressAnimHelper? = null

    override fun getLayoutId() = R.layout.activity_web

    override fun initView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        webView = findViewById(R.id.baseWebView)
        progressBar = findViewById(R.id.baseProgressBar)
        webView.setPageLoadListener(this)
        if (progressBar != null) {
            progressAnimHelper = ProgressAnimHelper(progressBar!!)
        }
        val s = intent.getStringExtra(TITLE)
        title = if (TextUtils.isEmpty(s)) "详情" else s
        baseWebView.loadUrl(intent.getStringExtra(URL))
        loadUrl(intent.getStringExtra(URL) ?: "")
    }

    fun loadUrl(url: String) {
        webView.doLoadUrl(url)
    }

    override fun onDestroy() {
        progressAnimHelper?.destroy()
        baseWebView.destroy()
        super.onDestroy()
//        exitProcess(0)
    }

    override fun onBackPressed() {
        if (baseWebView != null && baseWebView.canGoBack()) {
            baseWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun pageFinished() {
    }

    override fun pageLoadError() {

    }

    override fun progressChanged(newProgress: Int) {
        progressAnimHelper?.progressChanged(newProgress)
    }

    companion object {
        private const val TITLE = "TITLE"
        private const val URL = "URL"
        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(URL, url)
            context.startActivity(intent)
        }
    }
}

