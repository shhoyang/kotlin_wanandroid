package com.hao.library.http

/**
 * @author Yang Shihao
 */
interface HttpResponseModel<D> {
    fun getData(): D?
    fun getCode(): String
    fun getMessage(): String
    fun isSucceed(): Boolean
}