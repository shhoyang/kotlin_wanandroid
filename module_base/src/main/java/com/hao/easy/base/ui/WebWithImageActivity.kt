package com.hao.easy.base.ui

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.Fade
import android.transition.TransitionSet
import android.widget.ImageView
import com.hao.easy.base.R
import com.hao.easy.base.R.id.*
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

    var maxOffset = 0
    var percent = .0F

    override fun showToolbar() = false

    override fun getLayoutId() = R.layout.activity_web_with_image

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        var image = intent.getStringExtra(IMAGE)
        imageView.load(image)
        val s = intent.getStringExtra(TITLE)
        title = if (TextUtils.isEmpty(s)) "详情" else s
        baseWebView.progressBar = progressBar
        baseWebView.loadUrl(intent.getStringExtra(URL))
        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(p0: NestedScrollView?, p1: Int, p2: Int, p3: Int, p4: Int) {
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

