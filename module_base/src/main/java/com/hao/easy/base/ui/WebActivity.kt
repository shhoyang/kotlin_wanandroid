package com.hao.easy.base.ui

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.WindowManager
import com.hao.easy.base.common.ExtraKey
import com.hao.easy.base.databinding.ActivityWebBinding
import com.hao.easy.base.view.web.ProgressAnimHelper
import com.hao.easy.base.view.web.ProgressWebView

class WebActivity : BaseActivity<ActivityWebBinding, Nothing>(), ProgressWebView.PageLoadListener {

    private var progressAnimHelper: ProgressAnimHelper? = null

    override fun getVB() = ActivityWebBinding.inflate(layoutInflater)

    override fun getVM(): Nothing? = null

    override fun initView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        viewBinding {
            baseWebView.setPageLoadListener(this@WebActivity)
            progressAnimHelper = ProgressAnimHelper(baseProgressBar)
            val s = intent.getStringExtra(ExtraKey.STRING)
            title = if (TextUtils.isEmpty(s)) "详情" else s
            baseWebView.doLoadUrl(intent.getStringExtra(ExtraKey.STRING2) ?: "")
        }
    }

    override fun initData() {

    }

    override fun onDestroy() {
        progressAnimHelper?.destroy()
        viewBinding { baseWebView.destroy() }
        super.onDestroy()
//        exitProcess(0)
    }

    override fun onBackPressed() {
        viewBinding {
            if (baseWebView.canGoBack()) {
                baseWebView.goBack()
            } else {
                super.onBackPressed()
            }
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
        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(ExtraKey.STRING, title)
            intent.putExtra(ExtraKey.STRING2, url)
            context.startActivity(intent)
        }
    }
}

