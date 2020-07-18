package com.hao.easy.wan.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hao.easy.base.adapter.FragmentAdapter
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.wan.R
import com.hao.easy.wan.ui.adapter.BannerAdapter
import com.hao.easy.wan.viewmodel.WechatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wechat_fragment_wechat.*
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
@AndroidEntryPoint
class WechatFragment : BaseFragment() {

    private val viewModel: WechatViewModel by lazy {
        ViewModelProvider(this).get(WechatViewModel::class.java)
    }

    private var enableRefresh = true
    private var bannerHeight = 0

    private val titles = ArrayList<String>()
    private val fragments = ArrayList<Fragment>()
    private var fragmentCount = 0
    private var currentIndex = -1

    @Inject
    lateinit var bannerAdapter: BannerAdapter

    override fun getLayoutId() = R.layout.wechat_fragment_wechat

    override fun initView() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentIndex = position
            }
        })

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
                bannerHeight =
                    banner.measuredHeight - resources.getDimensionPixelSize(R.dimen.status_bar_height)
            }
            if (bannerHeight > 0) {
                banner.alpha = (bannerHeight + verticalOffset) * 1.0F / bannerHeight
            }
        })


        banner.adapter = bannerAdapter
        indicator.setViewPager(banner)
        bannerAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)
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
            bannerAdapter.setData(it)
        })

        viewModel.initData()
    }

    fun refreshFinished() {
        baseRefreshLayout.isRefreshing = false
    }
}