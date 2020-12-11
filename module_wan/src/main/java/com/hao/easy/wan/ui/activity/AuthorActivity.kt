package com.hao.easy.wan.ui.activity

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.hao.easy.base.adapter.listener.OnItemClickListener
import com.hao.easy.base.adapter.listener.OnItemLongClickListener
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.extensions.init
import com.hao.easy.base.extensions.visibility
import com.hao.easy.base.extensions.visible
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.base.ui.UIParams
import com.hao.easy.base.view.recycler.ItemTouchCallback
import com.hao.easy.base.view.recycler.ItemDragListener
import com.hao.easy.wan.R
import com.hao.easy.wan.db.Db
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.ui.adapter.AuthorAdapter
import com.hao.easy.wan.viewmodel.AuthorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wan_activity_author.*
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @Date 12/8/20
 */
@AndroidEntryPoint
class AuthorActivity : BaseActivity() {

    @Inject
    lateinit var subscribedAdapter: AuthorAdapter

    @Inject
    lateinit var otherAdapter: AuthorAdapter

    lateinit var itemTouchHelper: ItemTouchHelper

    private val viewModel by lazy {
        ViewModelProvider(this).get(AuthorViewModel::class.java)
    }

    override fun prepare(uiParams: UIParams, intent: Intent?) {
        uiParams.showToolbar = false
    }

    override fun getLayoutId() = R.layout.wan_activity_author

    override fun initView() {
        title = "选择订阅"

        itemTouchHelper = ItemTouchHelper(ItemTouchCallback(object : ItemDragListener {
            override fun draged(fromPosition: Int, toPosition: Int) {
                if (viewModel.sort(subscribedAdapter.data, fromPosition, toPosition)) {
                    subscribedAdapter.notifyItemMoved(fromPosition, toPosition)
                }
            }
        }))
        itemTouchHelper.attachToRecyclerView(subscribedRv)

        subscribedAdapter.setOnItemClickListener(object : OnItemClickListener<Author> {
            override fun itemClicked(holder: ViewHolder, view: View, item: Author, position: Int) {
                viewModel.cancelSubscribe(item)
            }
        })
        subscribedAdapter.setOnItemLongClickListener(object : OnItemLongClickListener<Author> {
            override fun itemLongClicked(
                holder: ViewHolder,
                view: View,
                item: Author,
                position: Int
            ) {
                if (!item.isImportant()) {
                    itemTouchHelper.startDrag(holder)
                }
            }
        })

        subscribedRv.init(subscribedAdapter, 4)
        otherAdapter.setOnItemClickListener(object : OnItemClickListener<Author> {
            override fun itemClicked(holder: ViewHolder, view: View, item: Author, position: Int) {
                viewModel.subscribe(item)
            }
        })
        otherRv.init(otherAdapter, 4)

        tvEdit.setOnClickListener {
            updateUI("编辑" == tvEdit.text)
        }
    }

    override fun initData() {
        Db.instance().authorDao().queryByVisible(Author.VISIBLE).observe(this) {
            subscribedAdapter.resetData(it)
        }
        Db.instance().authorDao().queryByVisible(Author.INVISIBLE).observe(this) {
            llOtherTitle.visibility(it.isNotEmpty())
            otherAdapter.resetData(it)
        }
        viewModel.getAuthors()
    }

    private fun updateUI(isEdit: Boolean) {
        if (isEdit) {
            tvEdit.text = "完成"
            tvSubscribedTip.visible()
            tvOtherTip.visible()
        } else {
            tvEdit.text = "编辑"
            tvSubscribedTip.gone()
            tvOtherTip.gone()
            viewModel.update(subscribedAdapter.data)
        }
        subscribedAdapter.isEdit = isEdit
        otherAdapter.isEdit = isEdit
        subscribedAdapter.notifyDataSetChanged()
        otherAdapter.notifyDataSetChanged()
    }
}