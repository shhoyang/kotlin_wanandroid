package com.hao.easy.wan.ui.adapter

import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.common.Icon
import com.hao.easy.wan.R
import com.hao.easy.wan.model.ProjectType
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/2
 */
class ProjectTypeAdapter @Inject constructor() : BaseNormalAdapter<ProjectType>(R.layout.wechat_item_project_type) {

    override fun bindViewHolder(holder: ViewHolder, item: ProjectType, position: Int) {
        holder.visible(R.id.tvText)
                .setText(R.id.tvText, item.name)
                .setImageResource(R.id.ivIcon, Icon.icons[position % 18])

    }
}