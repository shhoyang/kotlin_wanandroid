package com.hao.easy.wan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.extensions.load
import com.hao.easy.wan.databinding.WanItemBannerBinding
import com.hao.easy.wan.model.Ad
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class BannerAdapter @Inject constructor() : BaseNormalAdapter<WanItemBannerBinding, Ad>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemBannerBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(viewHolder: ViewHolder<WanItemBannerBinding>, item: Ad, position: Int) {
        viewHolder.viewBinding{root.load(item.imagePath)}
    }
}