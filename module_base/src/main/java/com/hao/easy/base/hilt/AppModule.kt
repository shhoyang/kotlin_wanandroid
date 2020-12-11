package com.hao.easy.base.hilt

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.socks.library.KLog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    private fun getBaseUrl() = "https://www.wanandroid.com"

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()


    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        persistentCookieJar: PersistentCookieJar
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cookieJar(persistentCookieJar)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            KLog.json(
                "json__",
                message
            )
        }

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    internal fun providePersistentCookieJar(sharedPrefsCookiePersistor: SharedPrefsCookiePersistor): PersistentCookieJar {
        return PersistentCookieJar(SetCookieCache(), sharedPrefsCookiePersistor)
    }

    @Provides
    @Singleton
    internal fun provideSharedPrefsCookiePersistor(@ApplicationContext context: Context): SharedPrefsCookiePersistor =
        SharedPrefsCookiePersistor(context)
}