package com.hao.easy.base.hilt

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cookie
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class HttpManager @Inject constructor(
    val cookies: SharedPrefsCookiePersistor,
    val persistentCookieJar: PersistentCookieJar,
    val okHttpClient: OkHttpClient,
    val retrofit: Retrofit
) {
    fun getCookies(): List<Cookie> {
        return cookies.loadAll()
    }

    fun cancelAllRequest(){
        okHttpClient.dispatcher.cancelAll()
        persistentCookieJar.clear()
    }
}