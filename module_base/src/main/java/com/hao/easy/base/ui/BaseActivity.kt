package com.hao.easy.base.ui

import android.app.Activity
import android.content.Intent
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

    protected val uiParams = UIParams()
    private var toolbar: ToolbarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance().pushActivity(this)
        prepare(uiParams, intent)
        if (uiParams.showToolbar) {
            setContentView(R.layout.activity_base)
            View.inflate(this, getLayoutId(), activityRootView)
        } else {
            setContentView(getLayoutId())
        }
        toolbar = findViewById(R.id.baseToolbar)
        toolbar?.let {
            it.setBackClickListener {
                onBackPressed()
            }
            processTitle(it)
        }
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance().popActivity(this)
    }

    open fun prepare(uiParams: UIParams, intent: Intent?) {

    }

    open fun initView() {
    }

    open fun initData() {
    }

    open fun processTitle(toolbarLayout: ToolbarLayout) {

    }

    override fun setTitle(title: CharSequence?) {
        toolbar?.title = title
    }

    fun toast(text: String?) {
        T.short(this, text)
    }

    fun toast(@StringRes resId: Int) {
        T.short(this, resId)
    }

    fun to(cls: Class<out Activity>, isFinish: Boolean = false) {
        startActivity(Intent(this, cls))
        if (isFinish) {
            finish()
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
}