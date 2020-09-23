package com.hao.easy.base.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.WindowManager
import com.hao.easy.base.R
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.include_web_progress_bar.*
import kotlin.system.exitProcess

open class WebActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_web

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )
        val s = intent.getStringExtra(TITLE)
        title = if (TextUtils.isEmpty(s)) "详情" else s
        baseWebView.progressBar = progressBar
        baseWebView.loadUrl(intent.getStringExtra(URL))
    }

    override fun onDestroy() {
        baseWebView.destroy()
        super.onDestroy()
        exitProcess(0)
    }

    override fun onBackPressed() {
        if (baseWebView != null && baseWebView.canGoBack()) {
            baseWebView.goBack()
        } else {
            super.onBackPressed()
        }
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

