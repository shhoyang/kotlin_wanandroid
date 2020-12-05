package com.hao.easy.user.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import com.hao.easy.base.adapter.OnItemClickListener
import com.hao.easy.base.adapter.OnItemLongClickListener
import com.hao.easy.base.extensions.init
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.base.utils.AppUtils
import com.hao.easy.user.R
import com.hao.easy.user.model.Menu
import com.hao.easy.user.ui.adapter.AboutAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.user_activity_about.*
import javax.inject.Inject

@AndroidEntryPoint
class AboutActivity : BaseActivity(), OnItemClickListener<Menu>, OnItemLongClickListener<Menu> {

    @Inject
    lateinit var adapter: AboutAdapter

    override fun getLayoutId() = R.layout.user_activity_about

    override fun initView() {
        title = "关于项目"
        tvVersion.text = "V${AppUtils.getVersionName(this)}"

        adapter.setOnItemClickListener(this)
        adapter.setOnItemLongClickListener(this)
        recyclerView.init(adapter)

        val list = ArrayList<Menu>()
        list.add(Menu("个人主页", HOME, "个人主页", HOME))
        list.add(Menu("Android项目链接", ANDROID_PROJECT_LINK, "玩Android", ANDROID_PROJECT_LINK))
        list.add(Menu("小程序项目链接", WECHAT_PROJECT_LINK, "玩Android", WECHAT_PROJECT_LINK))
        list.add(Menu("Apk下载地址", DOWNLOAD_LINK, "", DOWNLOAD_LINK))
        list.add(Menu("Thanks", "鸿洋-玩Android开发Api", "鸿洋-玩Android开发Api", THANKS))
        adapter.resetData(list)
    }

    private fun copy(text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text))
        toast("已复制到剪粘板")
    }

    override fun itemClicked(view: View, item: Menu, position: Int) {
        if (item.link == DOWNLOAD_LINK) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(DOWNLOAD_LINK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            WebActivity.start(this, item.webTitle, item.link)
        }
    }

    override fun itemLongClicked(view: View, item: Menu, position: Int) {
        copy(item.link)
    }

    companion object {
        const val HOME = "https://haoshiy.github.io"
        const val ANDROID_PROJECT_LINK = "https://github.com/haoshiy/kotlin_wanandroid"
        const val WECHAT_PROJECT_LINK = "https://github.com/haoshiy/wechat_wanandroid"
        const val DOWNLOAD_LINK = "http://fir.highstreet.top/kandroid"
        const val THANKS = "https://wanandroid.com/blog/show/2"
    }
}
