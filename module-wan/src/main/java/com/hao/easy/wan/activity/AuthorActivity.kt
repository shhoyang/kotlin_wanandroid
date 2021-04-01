package com.hao.easy.wan.activity

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import com.hao.easy.wan.R
import com.hao.easy.wan.adapter.AuthorAdapter
import com.hao.easy.wan.databinding.WanActivityAuthorBinding
import com.hao.easy.wan.db.Db
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.viewmodel.AuthorViewModel
import com.hao.library.adapter.listener.ItemDragListener
import com.hao.library.adapter.listener.ItemTouchCallback
import com.hao.library.adapter.listener.OnItemClickListener
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.annotation.Inject
import com.hao.library.extensions.gone
import com.hao.library.extensions.init
import com.hao.library.extensions.visibility
import com.hao.library.extensions.visible
import com.hao.library.ui.BaseActivity

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
class AuthorActivity : BaseActivity<WanActivityAuthorBinding, AuthorViewModel>() {

    @Inject
    lateinit var subscribedAdapter: AuthorAdapter

    @Inject
    lateinit var otherAdapter: AuthorAdapter

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun initView() {
        title = getString(R.string.wan_title_subscribe)
        itemTouchHelper = ItemTouchHelper(ItemTouchCallback(object : ItemDragListener {
            override fun dragged(fromPosition: Int, toPosition: Int) {
                viewModel {
                    if (sort(subscribedAdapter.data, fromPosition, toPosition)) {
                        subscribedAdapter.notifyItemMoved(fromPosition, toPosition)
                    }
                }
            }
        }))

        subscribedAdapter.itemTouchHelper = itemTouchHelper
        subscribedAdapter.setOnItemClickListener(object : OnItemClickListener<Author> {
            override fun itemClicked(
                view: View,
                item: Author,
                position: Int
            ) {
                viewModel { cancelSubscribe(item) }
            }
        })

        otherAdapter.setOnItemClickListener(object : OnItemClickListener<Author> {
            override fun itemClicked(
                view: View,
                item: Author,
                position: Int
            ) {
                viewModel { subscribe(item) }
            }
        })

        viewBinding {
            itemTouchHelper.attachToRecyclerView(subscribedRv)
            subscribedRv.init(subscribedAdapter, 4)
            otherRv.init(otherAdapter, 4)

            tvEdit.setOnClickListener {
                updateUI(getString(R.string.wan_edit) == tvEdit.text)
            }
        }
    }

    override fun initData() {
        Db.instance().authorDao().queryLiveDataByVisible(Author.VISIBLE).observe(this) {
            subscribedAdapter.resetData(it)
        }
        Db.instance().authorDao().queryLiveDataByVisible(Author.INVISIBLE).observe(this) {
            viewBinding { llOtherTitle.visibility(it.isNotEmpty()) }
            otherAdapter.resetData(it)
        }
        vm?.getAuthors()
    }

    private fun updateUI(isEdit: Boolean) {
        viewBinding {
            if (isEdit) {
                tvEdit.setText(R.string.wan_completed)
                tvSubscribedTip.visible()
                tvOtherTip.visible()
            } else {
                tvEdit.setText(R.string.wan_edit)
                tvSubscribedTip.gone()
                tvOtherTip.gone()
                viewModel {
                    update(subscribedAdapter.data)
                }
            }
        }
        subscribedAdapter.isEdit = isEdit
        otherAdapter.isEdit = isEdit
        subscribedAdapter.notifyDataSetChanged()
        otherAdapter.notifyDataSetChanged()
    }
}