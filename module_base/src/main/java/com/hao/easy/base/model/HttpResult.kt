package com.hao.easy.base.model

/**
 * @author Yang Shihao
 * @date 2018/11/19
 */
data class HttpResult<T>(var errorCode: Int,
                         var errorMsg: String,
                         var data: T)