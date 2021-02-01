package com.hao.library.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.hao.library.HaoLibrary
import com.hao.library.utils.L
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Yang Shihao
 */
object HttpManager {

    val COOKIE_CACHE = SetCookieCache()

    private val PERSISTENT_COOKIE_JAR =
        PersistentCookieJar(
            COOKIE_CACHE,
            SharedPrefsCookiePersistor(HaoLibrary.context)
        )

    private val OK_HTTP_CLIENT = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .cookieJar(PERSISTENT_COOKIE_JAR)
        .addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
            builder.method(original.method, original.body)
            chain.proceed(builder.build())
        })
        .addInterceptor(
            HttpLoggingInterceptor { message -> L.json("json__", message) }
                .setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
        )
        .build()

    val RETROFIT: Retrofit = Retrofit.Builder()
        .baseUrl(HaoLibrary.CONFIG.getBaseUrl())
        .client(OK_HTTP_CLIENT)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
                    .create()
            )
        )
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()


    fun cancelAllRequest() {
        OK_HTTP_CLIENT.dispatcher.cancelAll()
        PERSISTENT_COOKIE_JAR.clear()
    }
}