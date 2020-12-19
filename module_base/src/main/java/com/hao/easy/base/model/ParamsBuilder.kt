package com.hao.easy.base.model

import com.google.gson.Gson

/**
 * @author Yang Shihao
 * @Date 12/19/20
 */
class ParamsBuilder {

    private val map = HashMap<String, Any>()

    fun put(key: String, value: Any?): ParamsBuilder {
        if (null != value) {
            map[key] = value
        }
        return this
    }

    fun build(): HashMap<String, Any> {
        return map
    }

    fun toJson(): String {
        return Gson().toJson(map)
    }
}