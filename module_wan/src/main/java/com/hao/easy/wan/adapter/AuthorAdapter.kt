package com.hao.easy.wan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.hao.easy.wan.R
import com.hao.easy.wan.databinding.WanItemAuthorBinding
import com.hao.easy.wan.model.Author
import com.hao.library.adapter.BaseNormalAdapter
import com.hao.library.adapter.ViewHolder
import com.hao.library.extensions.gone
import com.hao.library.extensions.visible

/**
 * @author Yang Shihao
 */
class AuthorAdapter : BaseNormalAdapter<WanItemAuthorBinding, Author>() {

    var isEdit = false

    var itemTouchHelper: ItemTouchHelper? = null

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemAuthorBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemAuthorBinding>,
        item: Author,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val important = item.isImportant()
        val visible = item.isVisible()

        viewHolder.viewBinding {
            tvName.text = item.name
            tvName.isEnabled = !important

            if (!isEdit || important) {
                ivAction.gone()
                root.setOnClickListener(null)
            } else {
                ivAction.visible()
                ivAction.setImageResource(if (visible) R.drawable.wan_ic_delete_black else R.drawable.wan_ic_add_black)
                root.setOnClickListener {
                    itemClickListener?.itemClicked(it, item, position)
                }
            }

            if (visible) {
                root.setOnLongClickListener {
                    itemTouchHelper?.startDrag(viewHolder)
                    true
                }
            } else {
                root.setOnLongClickListener(null)
            }
        }
    }
}