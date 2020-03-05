package com.hao.easy.base.ui

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.hao.easy.base.R
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.extensions.load
import com.hao.easy.base.extensions.visible
import kotlinx.android.synthetic.main.activity_web_with_image.*
import kotlinx.android.synthetic.main.include_web_progress_bar.*

class WebWithImageActivity : WebActivity() {

    companion object {
        private const val TITLE = "TITLE"
        private const val URL = "URL"
        private const val IMAGE = "IMAGE"
        fun start(context: Context, title: String, url: String, imagePath: String) {
            val intent = Intent(context, WebWithImageActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(URL, url)
            intent.putExtra(IMAGE, imagePath)
            context.startActivity(intent)
        }
    }

    private var maxOffset = 0
    private var percent = .0F

    override fun transparentStatusBar() = true

    override fun getLayoutId() = R.layout.activity_web_with_image

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        val image = intent.getStringExtra(IMAGE)
        imageView.load(image)
        val s = intent.getStringExtra(TITLE)
        title = if (TextUtils.isEmpty(s)) "详情" else s
        baseWebView.progressBar = progressBar
        baseWebView.loadUrl(intent.getStringExtra(URL))
        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, p2, _, _ ->
            maxOffset = imageView.bottom - baseToolbar.bottom
            if (maxOffset <= 0) {
                imageView.alpha = 1.0F
                setToolbarColor(0.0F)
            } else {
                percent = p2 * 1.0F / maxOffset
                if (percent > 1.0F) {
                    percent = 1.0F
                }
                setToolbarColor(percent)
                imageView.alpha = 1.0F - percent
            }
            if (p2 >= maxOffset) {
                toolbarBg.visible()
                baseToolbar.showLine = true
            } else {
                toolbarBg.gone()
                baseToolbar.showLine = false
            }
        })
    }

    private val evaluator: ArgbEvaluator by lazy { ArgbEvaluator() }

    private fun setToolbarColor(positionOffset: Float) {
        val evaluate = evaluator.evaluate(
            positionOffset,
            Color.WHITE,
            ContextCompat.getColor(this, R.color.text_black)
        )
        baseToolbar.textColor = evaluate as Int
        baseToolbar.iconTintColor = evaluate
    }
}

