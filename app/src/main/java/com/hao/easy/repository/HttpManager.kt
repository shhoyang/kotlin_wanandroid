package com.hao.easy.repository

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Yang Shihao
 * @Date 2020/6/20
 */
@Singleton
data class HttpManager @Inject constructor(
    val cookies: SharedPrefsCookiePersistor,
    val persistentCookieJar: PersistentCookieJar,
    val okHttpClient: OkHttpClient,
    val retrofit: Retrofit
)