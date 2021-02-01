package com.hao.library.utils

import com.socks.library.KLog

/**
 * @author Yang Shihao
 */
object L {

    private const val DEFAULT_TAG = ""

    fun init(showLog: Boolean) {
        KLog.init(showLog)
    }

    fun d(msg: Any) {
        d(DEFAULT_TAG, msg)
    }

    fun d(tag: String, msg: Any) {
        KLog.d(tag, msg)
    }

    fun e(msg: Any) {
        e(DEFAULT_TAG, msg)
    }

    fun e(tag: String, msg: Any) {
        KLog.e(tag, msg)
    }

    fun json(msg: Any) {
        json(DEFAULT_TAG, msg.toString())
    }

    fun json(tag: String, msg: Any) {
        KLog.json(tag, msg.toString())
    }
}