package com.hao.easy.wan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.wan.R
import com.hao.easy.wan.databinding.WanItemProjectTypeBinding
import com.hao.easy.wan.databinding.WanItemProjectTypePageBinding
import com.hao.easy.wan.model.ProjectType
import com.hao.easy.wan.activity.ProjectArticleActivity
import com.hao.library.adapter.BaseNormalAdapter
import com.hao.library.adapter.ViewHolder
import com.hao.library.extensions.init

/**
 * @author Yang Shihao
 */
class ProjectTypePageAdapter :
    BaseNormalAdapter<WanItemProjectTypePageBinding, ArrayList<ProjectType>>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemProjectTypePageBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemProjectTypePageBinding>,
        item: ArrayList<ProjectType>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        viewHolder.viewBinding {
            root.init(ProjectTypeItemAdapter(item), 4)
        }
    }
}

class ProjectTypeItemAdapter(data: ArrayList<ProjectType>) :
    BaseNormalAdapter<WanItemProjectTypeBinding, ProjectType>(data) {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemProjectTypeBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemProjectTypeBinding>,
        item: ProjectType,
        position: Int,
        payloads: MutableList<Any>
    ) {
        viewHolder.viewBinding {
            tvText.text = item.name
            ivIcon.setImageResource(R.mipmap.ic_launcher_round)
            root.setOnClickListener {
                ProjectArticleActivity.start(root.context, item)
            }
        }
    }
}