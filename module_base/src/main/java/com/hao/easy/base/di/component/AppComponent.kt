package com.hao.easy.base.di.component

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.hao.easy.base.di.module.AppModule
import dagger.Component
import okhttp3.Cookie
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author Yang Shihao
 * @date 2018/12/7
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun retrofit(): Retrofit

    fun okHttpClient(): OkHttpClient

    fun persistentCookieJar(): PersistentCookieJar

    fun cookies(): List<Cookie>
}
