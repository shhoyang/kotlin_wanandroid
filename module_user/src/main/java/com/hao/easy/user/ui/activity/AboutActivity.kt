package com.hao.easy.user.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.widget.TextView
import com.hao.easy.base.extensions.snack
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.user.R
import kotlinx.android.synthetic.main.user_activity_about.*

class AboutActivity : BaseActivity(), View.OnLongClickListener {


    override fun getLayoutId() = R.layout.user_activity_about

    override fun initView() {
        title = "關於項目"
        tvVersion.text = getVersionName()
        tvDownloadLink.setOnLongClickListener(this)
        tvProjectLink.setOnLongClickListener(this)
        tvEmail.setOnLongClickListener(this)
        tvProjectLink.setOnClickListener {
            WebActivity.start(this, tvProjectLink.text.toString(), tvProjectLink.text.toString())
        }
    }

    private fun getVersionName(): String {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            "V${packageInfo.versionName}"
        } catch (e: PackageManager.NameNotFoundException) {
            ""
        }
    }

    private fun copy(text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText(null, text)
    }

    override fun onLongClick(v: View?): Boolean {
        copy((v as TextView).text.toString())
        v.snack("已複製到剪切板")
        return true
    }
}
