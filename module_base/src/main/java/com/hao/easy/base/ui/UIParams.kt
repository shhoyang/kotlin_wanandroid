package com.hao.easy.base.ui

/**
 * @author Yang Shihao
 * @Date 12/3/20
 */
data class UIParams(
    var showToolbar: Boolean = true,
    var isLazy: Boolean = false,
    var hasItemChange: Boolean = false,
    var hasItemRemove: Boolean = false
)