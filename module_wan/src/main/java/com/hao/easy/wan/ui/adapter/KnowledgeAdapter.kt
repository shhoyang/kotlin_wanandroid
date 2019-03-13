package com.hao.easy.wan.ui.adapter

import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.common.Icon
import com.hao.easy.base.extensions.init
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.ui.activity.KnowledgeArticleActivity
import com.library.flowlayout.FlowLayoutManager
import com.library.flowlayout.NestedRecyclerView
import javax.inject.Inject
import kotlin.random.Random

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class KnowledgeAdapter @Inject constructor() : BasePagedAdapter<Knowledge>(R.layout.wechat_item_knowledge_group) {

    override fun bindViewHolder(holder: ViewHolder, item: Knowledge, position: Int) {
        holder.setText(R.id.tvGroupName, item.name)
        val flowAdapter = FlowAdapter(item.children)
        val flowLayout = holder.getView<NestedRecyclerView>(R.id.flowLayout)
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

fun main() {
    test(1, c = 10)
}

fun test(a: Int, b: Int = 2, c: Int = 3, d: Int = 4) {

}

