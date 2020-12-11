package com.hao.easy.wan.ui.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hao.easy.base.BaseApplication
import com.hao.easy.base.adapter.FragmentAdapter
import com.hao.easy.base.adapter.FragmentCreator
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.wan.R
import com.hao.easy.wan.db.Db
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.ui.activity.AuthorActivity
import com.hao.easy.wan.ui.adapter.BannerAdapter
import com.hao.easy.wan.viewmodel.WechatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wan_fragment_wechat.*
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
@AndroidEntryPoint
class WechatFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(WechatViewModel::class.java)
    }

    private var statusBarHeight = 0
    private var maxScrollHeight = 0

    private var fragmentAdapter: FragmentAdapter? = null
    private val titles = ArrayList<String>()
    private val fragments = ArrayList<FragmentCreator>()
    private var fragmentCount = 0
    private var currentIndex = -1

    @Inject
    lateinit var bannerAdapter: BannerAdapter

    override fun getLayoutId() = R.layout.wan_fragment_wechat

    override fun initView() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentIndex = position
            }
        })

        baseRefreshLayout.setOnRefreshListener {
            doRefresh()
        }

        statusBarHeight = DisplayUtils.getStatusBarHeight(context ?: BaseApplication.instance)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->

            baseRefreshLayout.isEnabled = verticalOffset == 0
            if (maxScrollHeight <= 0) {
                maxScrollHeight =
                    banner.measuredHeight - statusBarHeight
            }
            if (maxScrollHeight > 0) {
                clTop.alpha = (maxScrollHeight + verticalOffset) * 1.0F / maxScrollHeight
            }
        })

        banner.adapter = bannerAdapter
        indicator.setViewPager(banner)
        bannerAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)

        ivAdd.setOnClickListener {
            to(AuthorActivity::class.java)
        }
    }

    private fun doRefresh(){
        if (currentIndex in 0 until fragmentCount) {
            val currentFragment = fragmentAdapter?.getFragment(currentIndex)
            if (currentFragment != null && currentFragment is WechatArticleFragment) {
                currentFragment.refresh()
            }
        }

       if(bannerAdapter.data.isEmpty()){
           viewModel.initData()
           baseRefreshLayout.isRefreshing = false
       }
    }

    override fun initData() {
        Db.instance().authorDao().queryByVisible(Author.VISIBLE).observe(this) {
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

        viewModel.adLiveData.observe(this) {
            bannerAdapter.resetData(it)
        }

        viewModel.initData()
    }

    fun refreshFinished() {
        baseRefreshLayout.isRefreshing = false
    }
}