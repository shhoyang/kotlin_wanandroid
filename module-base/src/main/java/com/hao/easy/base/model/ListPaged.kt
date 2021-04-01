package com.hao.easy.base.model

/**
 * @author Yang Shihao
 */
data class ListPaged<T>(
    var curPage: Int,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: ArrayList<T>
)