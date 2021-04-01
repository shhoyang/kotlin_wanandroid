package com.hao.easy.wan.fragment

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hao.easy.wan.R
import com.hao.easy.wan.activity.AuthorActivity
import com.hao.easy.wan.adapter.BannerAdapter
import com.hao.easy.wan.databinding.WanFragmentWechatBinding
import com.hao.easy.wan.db.Db
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.viewmodel.WechatViewModel
import com.hao.library.adapter.FragmentAdapter
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.annotation.Inject
import com.hao.library.extensions.loadCircle
import com.hao.library.ui.BaseFragment
import com.hao.library.ui.FragmentCreator

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
class WechatFragment : BaseFragment<WanFragmentWechatBinding, WechatViewModel>() {

    private var fragmentAdapter: FragmentAdapter? = null
    private val titles = ArrayList<String>()
    private val fragments = ArrayList<FragmentCreator>()
    private var fragmentCount = 0
    private var currentIndex = -1
    private var activityCallback: ActivityCallback? = null

    @Inject
    lateinit var bannerAdapter: BannerAdapter

    override fun initView() {
        viewBinding {
            ivAvatar.loadCircle(R.mipmap.wan_avatar)
            ivAvatar.setOnClickListener {
                activityCallback?.openDraw()
            }

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentIndex = position
                }
            })

            baseRefreshLayout.setOnRefreshListener {
                doRefresh()
            }


            appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                baseRefreshLayout.isEnabled = verticalOffset == 0
            })

            banner.adapter = bannerAdapter
            indicator.setViewPager(banner)
            bannerAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)

            ivAdd.setOnClickListener {
                toA(AuthorActivity::class.java)
            }
        }
    }

    private fun doRefresh() {
        if (currentIndex in 0 until fragmentCount) {
            val currentFragment = fragmentAdapter?.getFragment(currentIndex)
            if (currentFragment != null && currentFragment is WechatArticleFragment) {
                currentFragment.refresh()
            }
        }

        if (bannerAdapter.data.isEmpty()) {
            viewModel { initData() }
            viewBinding { baseRefreshLayout.isRefreshing = false }
        }
    }

    override fun initData() {
        Db.instance().authorDao().queryLiveDataByVisible(Author.VISIBLE).observe(this) {
            if (it.isNotEmpty()) {
                titles.clear()
                fragments.clear()
                it.forEach { author ->
                    titles.add(author.name)
                    fragments.add(object : FragmentCreator {
                        override fun createFragment() = WechatArticleFragment.instance(author.id)
                    })
                }
                fragmentCount = fragments.size
                fragmentAdapter = FragmentAdapter(childFragmentManager, lifecycle, fragments)
                viewBinding {
                    viewPager.adapter = fragmentAdapter
                    tabLayout.tabMode =
                        if (it.size > 4) TabLayout.MODE_SCROLLABLE else TabLayout.MODE_FIXED
                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                        if (position in 0 until fragmentCount) {
                            tab.text = titles[position]
                        }
                    }.attach()
                }
            }
        }

        viewModel {
            adLiveData.observe(this@WechatFragment) {
                bannerAdapter.resetData(it)
            }
            initData()
        }
    }

    fun refreshFinished() {
        viewBinding { baseRefreshLayout.isRefreshing = false }
    }

    fun setActivityCallback(activityCallback: ActivityCallback) {
        this.activityCallback = activityCallback
    }

    interface ActivityCallback {
        fun openDraw()
    }
}