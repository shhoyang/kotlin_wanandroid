package com.hao.easy.wan.ui.fragment

import android.content.Context
import android.view.MotionEvent
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hao.easy.base.adapter.FragmentAdapter
import com.hao.easy.base.extensions.load
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.wan.R
import com.hao.easy.wan.viewmodel.WechatViewModel
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.wechat_fragment_wechat.*
import org.jetbrains.anko.support.v4.dimen
import kotlin.math.abs

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
class WechatFragment : BaseFragment() {

    private val viewModel: WechatViewModel by lazy {
        ViewModelProviders.of(this@WechatFragment).get(WechatViewModel::class.java)
    }

    private var startX = .0F
    private var startY = .0F
    private var enableRefresh = true
    private var bannerInit = false
    private var bannerHeight = 0

    private val titles = ArrayList<String>()
    private val fragments = ArrayList<Fragment>()
    private var fragmentCount = 0
    private var currentIndex = -1

    override fun getLayoutId() = R.layout.wechat_fragment_wechat

    override fun initView() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentIndex = position
            }
        })
        viewPager.setOnTouchListener { _, ev ->
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = ev.x
                    startY = ev.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val endX = ev.x
                    val endY = ev.y
                    val distanceX = abs(startX - endX)
                    val distanceY = abs(startY - endY)
                    if (distanceX > distanceY) {
                        baseRefreshLayout.isEnabled = false
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    enableRefresh = true
                }
            }
            false
        }

        baseRefreshLayout.setOnRefreshListener {
            if (currentIndex in 0 until fragmentCount) {
                val currentFragment = fragments[currentIndex]
                if (currentFragment != null && currentFragment.isAdded && currentFragment is WechatArticleFragment) {
                    currentFragment.refresh()
                    return@setOnRefreshListener
                }
            }

            viewModel.initData()
            baseRefreshLayout.isRefreshing = false
        }

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            baseRefreshLayout.isEnabled = verticalOffset == 0 && enableRefresh
            if (bannerHeight <= 0) {
                bannerHeight = banner.measuredHeight - dimen(R.dimen.status_bar_height)
            }
            if (bannerHeight > 0) {
                banner.alpha = (bannerHeight + verticalOffset) * 1.0F / bannerHeight
            }
        })

        banner.setBannerStyle(BannerConfig.NOT_INDICATOR)
            .setImageLoader(object : ImageLoader() {
                override fun displayImage(context: Context?, path: Any, imageView: ImageView) {
                    imageView.load(path)
                }
            })
            .start()
        bannerInit = true
    }

    override fun initData() {
        viewModel.authorsLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                titles.clear()
                fragments.clear()
                it.forEach { author ->
                    titles.add(author.name)
                    fragments.add(WechatArticleFragment.instance(author.id))
                }
                fragmentCount = fragments.size
                val adapter = FragmentAdapter(childFragmentManager, lifecycle, fragments)
                viewPager.adapter = adapter
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    if (position in 0 until fragmentCount) {
                        tab.text = titles[position]
                    }
                }.attach()
            }
        })

        viewModel.adLiveData.observe(this, Observer {
            banner.update(it)
        })

        viewModel.initData()
    }

    fun refreshFinished() {
        baseRefreshLayout.isRefreshing = false
    }
}