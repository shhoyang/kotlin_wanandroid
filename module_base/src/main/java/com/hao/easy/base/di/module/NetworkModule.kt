package com.hao.easy.base.di.module

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.hao.easy.base.App
import com.socks.library.KLog
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Yang Shihao
 * @date 2018/10/23
 */
@Module
class NetworkModule {

    private fun getBaseUrl() = "https://www.wanandroid.com"

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(getBaseUrl())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()


    @Provides
    @Singleton
    internal fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                                     persistentCookieJar: PersistentCookieJar): OkHttpClient =
            OkHttpClient.Builder()
                    .cookieJar(persistentCookieJar)
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor {
                KLog.json("json____", it)
            }.setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    internal fun providePersistentCookieJar(sharedPrefsCookiePersistor: SharedPrefsCookiePersistor): PersistentCookieJar {
        return PersistentCookieJar(SetCookieCache(), sharedPrefsCookiePersistor)
    }


    @Provides
    @Singleton
    internal fun provideSharedPrefsCookiePersistor(): SharedPrefsCookiePersistor =
            SharedPrefsCookiePersistor(App.instance)

    @Provides
    @Singleton
    internal fun provideCookies(sharedPrefsCookiePersistor: SharedPrefsCookiePersistor) =
            sharedPrefsCookiePersistor.loadAll()
}