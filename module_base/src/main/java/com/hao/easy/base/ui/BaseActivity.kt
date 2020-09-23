package com.hao.easy.base.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.hao.easy.base.R
import com.hao.easy.base.common.AppManager
import com.hao.easy.base.utils.T
import com.hao.easy.base.view.ToolbarLayout
import kotlinx.android.synthetic.main.activity_base.*

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private const val STATUS_BAR_DARK = 0
        private const val STATUS_BAR_LIGHT = 1
    }

    private var toolbar: ToolbarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance().pushActivity(this)
        if (!showToolbar()) {
            setContentView(getLayoutId())
        } else {
            setContentView(R.layout.activity_base)
            View.inflate(this, getLayoutId(), activityRootView)
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
    fun setStatusBarMode(mode: Int, transparentStatusBar: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var systemUiVisibility = window.decorView.systemUiVisibility

            if (STATUS_BAR_LIGHT == mode) {
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else if (STATUS_BAR_DARK == mode) {
                systemUiVisibility =
                    systemUiVisibility and (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
            }

            if (transparentStatusBar) {
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }

            window.decorView.systemUiVisibility = systemUiVisibility
        }
    }

    open fun showToolbar() = true

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun initView() {
    }

    open fun initData() {
    }

    override fun setTitle(title: CharSequence?) {
        toolbar?.title = title
    }

    /**
     * 设置透明状态栏，耗时5ms
     * 只在主题设置，某些机型会是半透明的
     *
     * @param lightStatusBar true深色文字，false浅色文字
     */
    fun transparentStatusBar(lightStatusBar: Boolean = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (lightStatusBar) {
                var systemUiVisibility = (window.decorView.systemUiVisibility
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                window.decorView.systemUiVisibility = systemUiVisibility
            } else {
                var systemUiVisibility = (window.decorView.systemUiVisibility
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
                window.decorView.systemUiVisibility = systemUiVisibility
            }
        }
    }

    fun toast(msg: String?) {
        T.short(this, msg)
    }

    fun toast(@StringRes resId: Int) {
        T.short(this, resId)
    }
}