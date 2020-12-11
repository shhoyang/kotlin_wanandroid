package com.hao.easy.base.ui

/**
 * @author Yang Shihao
 * @Date 12/3/20
 */
data class UIParams(
    var showToolbar: Boolean = true,
    // 从状态栏布局
    var isTransparentStatusBar:Boolean = false,
    var isLazy: Boolean = false,
    var hasItemChange: Boolean = true,
    var hasItemRemove: Boolean = true
)