package com.hao.easy.wan.ui.activity

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.hao.easy.base.adapter.listener.OnItemClickListener
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.extensions.init
import com.hao.easy.base.extensions.visibility
import com.hao.easy.base.extensions.visible
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.base.view.recycler.ItemDragListener
import com.hao.easy.base.view.recycler.ItemTouchCallback
import com.hao.easy.wan.databinding.WanActivityAuthorBinding
import com.hao.easy.wan.db.Db
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.ui.adapter.AuthorAdapter
import com.hao.easy.wan.viewmodel.AuthorViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @Date 12/8/20
 */
@AndroidEntryPoint
class AuthorActivity : BaseActivity<WanActivityAuthorBinding, AuthorViewModel>() {

    @Inject
    lateinit var subscribedAdapter: AuthorAdapter

    @Inject
    lateinit var otherAdapter: AuthorAdapter

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun getVB() = WanActivityAuthorBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(AuthorViewModel::class.java)

    override fun initView() {
        title = "选择订阅"
        itemTouchHelper = ItemTouchHelper(ItemTouchCallback(object : ItemDragListener {
            override fun draged(fromPosition: Int, toPosition: Int) {
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
                updateUI("编辑" == tvEdit.text)
            }
        }
    }

    override fun initData() {
        Db.instance().authorDao().queryByVisible(Author.VISIBLE).observe(this, Observer {
            subscribedAdapter.resetData(it)
        })
        Db.instance().authorDao().queryByVisible(Author.INVISIBLE).observe(this, Observer {
            viewBinding { llOtherTitle.visibility(it.isNotEmpty()) }
            otherAdapter.resetData(it)
        })
        viewModel { getAuthors() }
    }

    private fun updateUI(isEdit: Boolean) {
        viewBinding {
            if (isEdit) {
                tvEdit.text = "完成"
                tvSubscribedTip.visible()
                tvOtherTip.visible()
            } else {
                tvEdit.text = "编辑"
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