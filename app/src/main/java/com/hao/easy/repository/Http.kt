package com.hao.easy.repository

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.hao.easy.App
import com.socks.library.KLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Yang Shihao
 * @Date 2020/6/20
 */
class Http private constructor() {

    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null
    private var persistentCookieJar: PersistentCookieJar? = null
    private var sharedPrefsCookiePersistor: SharedPrefsCookiePersistor? = null

    fun retrofit(): Retrofit {
        if (retrofit == null) {
            sharedPrefsCookiePersistor = SharedPrefsCookiePersistor(App.instance)
            persistentCookieJar = PersistentCookieJar(SetCookieCache(), sharedPrefsCookiePersistor)

            val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .cookieJar(persistentCookieJar!!)
                .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        KLog.json("json__", message)
                    }

                }).setLevel(HttpLoggingInterceptor.Level.BODY))

//                if (!AppConfig.isDebug()) {
//                    okHttpClientBuilder.proxy(Proxy.NO_PROXY)
//                }

            okHttpClient = okHttpClientBuilder.build()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun clearCookie() {
        persistentCookieJar?.clear()
    }

    fun cancelAllRequest() {
        okHttpClient?.dispatcher?.cancelAll()
    }

    fun cookies() = sharedPrefsCookiePersistor

    companion object {

        private const val BASE_URL = "https://www.wanandroid.com"

        private var instance: Http? = null

        @Synchronized
        fun instance(): Http {
            if (instance == null) {
                instance = Http()
            }
            return instance!!
        }
    }
}