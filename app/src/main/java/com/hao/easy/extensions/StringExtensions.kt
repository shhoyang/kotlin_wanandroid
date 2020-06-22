package com.hao.easy.extensions

/**
 * @author Yang Shihao
 */

fun String.removeSymbol():String{
    return replace(Regex("&[^;]+;"), "")
}

fun String.removeHtml():String{
    return replace(Regex("<[^>]+>"), "")
}