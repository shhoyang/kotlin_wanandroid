package com.hao.easy.base.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.hao.easy.base.R
import com.hao.easy.base.common.AppManager
import com.hao.easy.base.view.ToolbarLayout
import kotlinx.android.synthetic.main.activity_base.*
import kotlin.properties.Delegates

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private const val STATUS_BAR_NONE = -1
        private const val STATUS_BAR_DARK = 0
        private const val STATUS_BAR_LIGHT = 1
    }

    /**
     * 状态栏模式变化
     */
    private var statusBarMode by Delegates.observable(STATUS_BAR_NONE) { _, old, new ->
        if (old != new) {
            statusBar(new)
        }
    }

    private var toolbar: ToolbarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarMode(true)
        AppManager.instance().pushActivity(this)
        when {
            getLayoutId() == 0 -> setContentView()

            !showToolbar()-> setContentView(getLayoutId())
            transparentStatusBar() -> setContentView(getLayoutId())

            else -> {
                setContentView(R.layout.activity_base)
                View.inflate(this, getLayoutId(), activityRootView)
            }
        }
        toolbar = findViewById(R.id.baseToolbar)
        toolbar?.apply {
            setBackClickListener {
                onBackPressed()
            }
        }
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance().popActivity(this)
    }

    /**
     * 改变状态栏样式
     */
    fun setStatusBarMode(light: Boolean) {
        if (isFullScreen()) {
            return
        }
        statusBarMode = if (light) {
            STATUS_BAR_LIGHT
        } else {
            STATUS_BAR_DARK
        }
    }

    /**
     * 设置状态栏样式
     */
    private fun statusBar(mode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val systemUiVisibility = window.decorView.systemUiVisibility
            if (transparentStatusBar()) {
                window.decorView.systemUiVisibility = when (mode) {
                    // 深色文字
                    STATUS_BAR_LIGHT -> systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    STATUS_BAR_DARK -> systemUiVisibility and (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()) or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    else -> systemUiVisibility
                }
            } else {
                window.decorView.systemUiVisibility = when (mode) {
                    // 深色文字
                    STATUS_BAR_LIGHT -> systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    STATUS_BAR_DARK -> systemUiVisibility and (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
                    else -> systemUiVisibility
                }
            }
        }
    }

    /**
     * 是否全屏
     */
    open fun isFullScreen(): Boolean = false

    /**
     * 是否透明状态栏，布局从状态栏开始
     */
    open fun transparentStatusBar(): Boolean = false

    open fun showToolbar() = true

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun setContentView() {
    }

    open fun initView() {
    }

    open fun initData() {
    }

    override fun setTitle(title: CharSequence?) {
        toolbar?.title = title
    }

    fun <T : View> f(id: Int): T? {
        return findViewById(id)
    }
}