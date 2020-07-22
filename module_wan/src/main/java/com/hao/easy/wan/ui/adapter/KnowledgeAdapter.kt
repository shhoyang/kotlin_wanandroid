package com.hao.easy.wan.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hao.easy.base.BaseApplication
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.OnItemClickListener
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.common.Icon
import com.hao.easy.base.extensions.init
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Knowledge
import com.library.flowlayout.FlowLayoutManager
import com.library.flowlayout.SpaceItemDecoration
import javax.inject.Inject
import kotlin.random.Random

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class KnowledgeAdapter @Inject constructor() :
    BasePagedAdapter<Knowledge>(R.layout.wan_item_knowledge_group) {

    private val recycledViewPool = RecyclerView.RecycledViewPool()
    private val spaceItemDecoration =
        SpaceItemDecoration(DisplayUtils.dp2px(BaseApplication.instance, 4))

    override fun bindViewHolder(holder: ViewHolder, item: Knowledge, position: Int) {
        holder.setText(R.id.tvGroupName, item.name)
        val flowAdapter = FlowAdapter(item.children, itemClickListener)
        val rvChild = holder.getView<RecyclerView>(R.id.rvChild)
        rvChild.setRecycledViewPool(recycledViewPool)
        if (holder.itemView.tag == null) {
            rvChild.addItemDecoration(spaceItemDecoration)
            holder.itemView.tag = "item"
        }
        rvChild.init(flowAdapter, FlowLayoutManager())
    }
}

class FlowAdapter(
    data: ArrayList<Knowledge>,
    private val clickListener: OnItemClickListener<Knowledge>?
) :
    BaseNormalAdapter<Knowledge>(R.layout.wan_item_knowledge_child, data) {

    override fun bindViewHolder(holder: ViewHolder, item: Knowledge, position: Int) {
        holder.setText(R.id.tvChildName, item.name)
            .setImageResource(R.id.ivIcon, Icon.icons[Random.nextInt(Icon.icons.size)])
            .itemView.setOnClickListener {
                clickListener?.itemClicked(it, item, position)
            }
    }
}
