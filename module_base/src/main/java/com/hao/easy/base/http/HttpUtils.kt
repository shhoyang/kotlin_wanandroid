package com.hao.easy.base.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.hao.easy.base.BaseApplication
import com.socks.library.KLog
import okhttp3.Cookie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Yang Shihao
 * @Date 2020/7/17
 */
class HttpUtils private constructor() {

    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null
    private val persistentCookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), sharedPrefsCookiePersistor)
    }
    private val sharedPrefsCookiePersistor: SharedPrefsCookiePersistor by lazy {
        SharedPrefsCookiePersistor(BaseApplication.instance)
    }

    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = buildRetrofit()
        }
        return retrofit!!
    }

    private fun buildRetrofit(): Retrofit {
        okHttpClient = OkHttpClient.Builder()
            .cookieJar(persistentCookieJar)
            .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    KLog.json("json__", message)
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun getCookies(): MutableList<Cookie>? = sharedPrefsCookiePersistor.loadAll()

    fun cancelAllRequest() {
        okHttpClient?.dispatcher?.cancelAll()
        persistentCookieJar.clear()
    }

    companion object {
        private const val BASE_URL = "https://www.wanandroid.com"
        private var instance: HttpUtils? = null
            get() {
                if (field == null) {
                    field = HttpUtils()
                }
                return field
            }

        @Synchronized
        fun instance(): HttpUtils {
            return instance!!
        }
    }
}