package com.hao.library.ui

import android.content.Context
import android.content.Intent
import android.view.WindowManager
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.databinding.ActivityWebBinding
import com.hao.library.view.web.ProgressAnimHelper
import com.hao.library.view.web.ProgressWebView
import com.hao.library.viewmodel.PlaceholderViewModel

@AndroidEntryPoint(injectViewModel = false)
class WebActivity : BaseActivity<ActivityWebBinding, PlaceholderViewModel>(),
    ProgressWebView.PageLoadListener {

    private var progressAnimHelper: ProgressAnimHelper? = null

    override fun initView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        viewBinding {
            baseWebView.setPageLoadListener(this@WebActivity)
            progressAnimHelper = ProgressAnimHelper(baseProgressBar)
            title = intent.getStringExtra(TITLE)
            baseWebView.doLoadUrl(intent.getStringExtra(URL) ?: "")
        }
    }

    override fun initData() {

    }

    override fun onDestroy() {
        progressAnimHelper?.destroy()
        viewBinding { baseWebView.destroy() }
        super.onDestroy()
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

