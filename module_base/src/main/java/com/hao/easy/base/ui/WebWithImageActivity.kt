package com.hao.easy.base.ui

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import com.hao.easy.base.R
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.extensions.load
import com.hao.easy.base.extensions.visible
import kotlinx.android.synthetic.main.activity_web_with_image.*
import kotlinx.android.synthetic.main.include_web_progress_bar.*
import org.jetbrains.anko.startActivity

class WebWithImageActivity : WebActivity() {

    companion object {
        private const val TITLE = "TITLE"
        private const val URL = "URL"
        private const val IMAGE = "IMAGE"
        fun start(context: Context, title: String, url: String, imagePath: String) {
            context.startActivity<WebWithImageActivity>(Pair(TITLE, title), Pair(URL, url), Pair(IMAGE, imagePath))
        }
    }

    private var maxOffset = 0
    private var percent = .0F

    override fun showToolbar() = false

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
        val evaluate = evaluator.evaluate(positionOffset, Color.WHITE, ContextCompat.getColor(this, R.color.text_black))
        baseToolbar.textColor = evaluate as Int
        baseToolbar.iconTintColor = evaluate
    }
}

