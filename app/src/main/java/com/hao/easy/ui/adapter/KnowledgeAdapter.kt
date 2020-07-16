package com.hao.easy.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.common.Icon
import com.hao.easy.base.extensions.init
import com.hao.easy.R
import com.hao.easy.model.Knowledge
import com.hao.easy.ui.activity.KnowledgeArticleActivity
import com.library.flowlayout.FlowLayoutManager
import com.library.flowlayout.NestedRecyclerView
import javax.inject.Inject
import kotlin.random.Random

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class KnowledgeAdapter @Inject constructor() : BasePagedAdapter<Knowledge>(R.layout.wechat_item_knowledge_group) {

    private val recycledViewPool = RecyclerView.RecycledViewPool()

    override fun bindViewHolder(holder: ViewHolder, item: Knowledge, position: Int) {
        holder.setText(R.id.tvGroupName, item.name)
        val flowAdapter = FlowAdapter(item.children)
        val flowLayout = holder.getView<NestedRecyclerView>(R.id.flowLayout)
        flowLayout.setRecycledViewPool(recycledViewPool)
        flowLayout.init(flowAdapter, FlowLayoutManager())
    }
}

class FlowAdapter(data: ArrayList<Knowledge>) :
    BaseNormalAdapter<Knowledge>(R.layout.wechat_item_knowledge_child, data) {

    override fun bindViewHolder(holder: ViewHolder, item: Knowledge, position: Int) {
        holder.setText(R.id.tvChildName, item.name)
            .setImageResource(R.id.ivIcon, Icon.icons[Random.nextInt(Icon.icons.size)])
            .itemView.setOnClickListener {
            KnowledgeArticleActivity.start(context, item)
        }
    }
}
