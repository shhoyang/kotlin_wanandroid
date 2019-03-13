package com.hao.easy.wan.ui.fragment

import android.arch.lifecycle.Observer
import android.support.design.widget.AppBarLayout
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.gcssloop.widget.PagerGridLayoutManager
import com.gcssloop.widget.PagerGridSnapHelper
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.extensions.visible
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.base.ui.WebWithImageActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.di.component
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.ui.activity.ProjectArticleActivity
import com.hao.easy.wan.ui.adapter.ProjectArticleAdapter
import com.hao.easy.wan.ui.adapter.ProjectTypeAdapter
import com.hao.easy.wan.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.wechat_fragment_project.*
import org.jetbrains.anko.support.v4.dip
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
class ProjectFragment : BaseListFragment<Article, ProjectViewModel>() {

    @Inject
    lateinit var adapter: ProjectArticleAdapter

    @Inject
    lateinit var typeAdapter: ProjectTypeAdapter

    private var startX = .0F
    private var startY = .0F
    private var enableRefresh = true
    private var appBarOffset = 0

    override fun getLayoutId() = R.layout.wechat_fragment_project

    override fun initInject() {
        component().inject(this)
    }

    override fun initView() {
        super.initView()

        rvType.setOnTouchListener { _, ev ->
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
                    baseRefreshLayout.isEnabled = appBarOffset == 0 && enableRefresh
                }
            }
            false
        }

        typeAdapter.itemClickListener = { _, item, _ ->
            this@ProjectFragment.context?.apply {
                ProjectArticleActivity.start(this, item)
            }
        }

        val pagerGridLayoutManager = PagerGridLayoutManager(2, 4, PagerGridLayoutManager.HORIZONTAL)
        pagerGridLayoutManager.setPageListener(object : PagerGridLayoutManager.PageListener {
            override fun onPageSelect(pageIndex: Int) {
                if (pageIndex != currentPager && pageIndex < points.size) {
                    points[pageIndex].setImageResource(R.drawable.wechat_indicator_1)
                    points[currentPager].setImageResource(R.drawable.wechat_indicator_0)
                    currentPager = pageIndex
                }
            }

            override fun onPageSizeChanged(pageSize: Int) {
            }
        })

        rvType.apply {
            layoutManager = pagerGridLayoutManager
            PagerGridSnapHelper().attachToRecyclerView(this)
            adapter = typeAdapter
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            appBarOffset = verticalOffset
            baseRefreshLayout.isEnabled = appBarOffset == 0 && enableRefresh
        })
    }

    override fun initData() {
        super.initData()
        viewModel.typeLiveData.observe(this, Observer {
            typeAdapter.setData(it!!)
            line.visible()
            createPoints(it.size)
        })
        lifecycle.addObserver(viewModel)
    }

    override fun adapter() = adapter

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> {
                context?.apply {
                    WebActivity.start(this, item.title, item.projectLink)
                }
            }
            R.id.ivFav -> {
                if (item.collect) {
                    viewModel.cancelCollect(item, position)
                } else {
                    viewModel.collect(item, position)
                }
            }
            else -> context?.apply {
                WebWithImageActivity.start(this, item.title, item.link, item.envelopePic)
            }
        }
    }

    private val points = ArrayList<ImageView>()

    private var currentPager = 0

    private fun createPoints(itemSize: Int) {
        if (itemSize < 9) {
            llPoint.gone()
            return
        }
        val count = if (itemSize % 8 == 0) itemSize / 8 else itemSize / 8 + 1
        points.clear()
        llPoint.removeAllViews()
        currentPager = 0
        val layoutParams = LinearLayout.LayoutParams(dip(8), dip(8))
        layoutParams.leftMargin = dip(4)
        layoutParams.rightMargin = dip(4)
        for (i in 0 until count) {
            val imageView = ImageView(this@ProjectFragment.context)
            imageView.layoutParams = layoutParams
            if (i == 0) {
                imageView.setImageResource(R.drawable.wechat_indicator_1)
            } else {
                imageView.setImageResource(R.drawable.wechat_indicator_0)
            }
            points.add(imageView)
            llPoint.addView(imageView)
        }
        llPoint.visible()
    }
}