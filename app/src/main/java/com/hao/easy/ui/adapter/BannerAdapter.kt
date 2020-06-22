package com.hao.easy.ui.adapter

import android.widget.ImageView
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.extensions.load
import com.hao.easy.R
import com.hao.easy.model.Ad
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class BannerAdapter @Inject constructor() : BaseNormalAdapter<Ad>(R.layout.wechat_item_banner) {

    override fun bindViewHolder(holder: ViewHolder, item: Ad, position: Int) {
        (holder.itemView as ImageView).load(item.imagePath)
    }
}