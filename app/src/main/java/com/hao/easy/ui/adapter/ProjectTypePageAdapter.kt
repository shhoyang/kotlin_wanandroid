package com.hao.easy.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.common.Icon
import com.hao.easy.base.extensions.init
import com.hao.easy.R
import com.hao.easy.model.ProjectType
import com.hao.easy.ui.activity.ProjectArticleActivity
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/2
 */
class ProjectTypePageAdapter @Inject constructor() :
    BaseNormalAdapter<ArrayList<ProjectType>>(R.layout.wechat_item_project_type_page) {

    override fun bindViewHolder(holder: ViewHolder, item: ArrayList<ProjectType>, position: Int) {
        val recyclerView = holder.itemView as RecyclerView
        recyclerView.init(ProjectTypeItemAdapter(item), 4)
    }
}

class ProjectTypeItemAdapter(data: ArrayList<ProjectType>) :
    BaseNormalAdapter<ProjectType>(R.layout.wechat_item_project_type, data) {

    override fun bindViewHolder(holder: ViewHolder, item: ProjectType, position: Int) {
        holder.setText(R.id.tvText, item.name)
            .setImageResource(R.id.ivIcon, Icon.icons[position % 18])
        holder.itemView.setOnClickListener {
            ProjectArticleActivity.start(context, item)
        }
    }
}