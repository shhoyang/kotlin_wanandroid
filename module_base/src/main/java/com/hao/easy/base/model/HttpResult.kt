package com.hao.easy.base.model

import com.hao.library.http.HttpResponseModel

/**
 * @author Yang Shihao
 */
data class HttpResult<D>(
    var errorCode: Int,
    var errorMsg: String,
    @JvmField
    var data: D?
) : HttpResponseModel<D> {

    override fun getData(): D? {
        return data
    }

    override fun getCode(): String {
        return errorCode.toString()
    }

    override fun getMessage(): String {
        return errorMsg
    }

    override fun isSucceed(): Boolean {
        return 0 == errorCode
    }
}