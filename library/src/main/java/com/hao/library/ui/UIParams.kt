package com.hao.library.ui

/**
 * @author Yang Shihao
 */
data class UIParams(
//    var showToolbar: Boolean = true,
    // 从状态栏布局
    var isTransparentStatusBar:Boolean = false,
    var isLazy: Boolean = false,
    var hasItemChange: Boolean = true,
    var hasItemRemove: Boolean = true
)