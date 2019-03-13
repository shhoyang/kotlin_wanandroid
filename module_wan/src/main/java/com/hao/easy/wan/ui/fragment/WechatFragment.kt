package com.hao.easy.wan.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.design.widget.AppBarLayout
import android.view.MotionEvent
import android.widget.ImageView
import com.hao.easy.base.extensions.load
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.wan.R
import com.hao.easy.wan.ui.adapter.FragmentWithTabAdapter
import com.hao.easy.wan.viewmodel.WechatViewModel
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.wechat_fragment_wechat.*
import org.jetbrains.anko.support.v4.dimen

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
class WechatFragment : BaseFragment() {

    private val viewModel: WechatViewModel by lazy {
        ViewModelProviders.of(this@WechatFragment).get(WechatViewModel::class.java)
    }

    private var adapter: FragmentWithTabAdapter? = null

    private var startX = .0F
    private var startY = .0F
    private var enableRefresh = true
    private var bannerInit = false
    private var bannerHeight = 0

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (bannerInit) {
            if (isVisibleToUser) {
                banner.startAutoPlay()
            } else {
                banner.stopAutoPlay()
            }
        }
    }

    override fun getLayoutId() = R.layout.wechat_fragment_wechat

    override fun initView() {
        viewPager.setOnTouchListener { _, ev ->
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = ev.x
                    startY = ev.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val endX = ev.x
                    val endY = ev.y
                    val distanceX = Math.abs(startX - endX)
                    val distanceY = Math.abs(startY - endY)
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
            val currentFragment = adapter?.currentFragment
            if (currentFragment == null) {
                viewModel.initData()
                baseRefreshLayout.isRefreshing = false

            } else if (currentFragment is WechatArticleFragment) {
                currentFragment.refresh()
            }
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

        tabLayout.setupWithViewPager(viewPager)
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
            adapter = FragmentWithTabAdapter(childFragmentManager, it!!)
            viewPager.adapter = adapter
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