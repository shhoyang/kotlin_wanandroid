package com.hao.easy.base.di.module

import android.app.Application
import dagger.Module
import dagger.Provides

/**
 * @author Yang Shihao
 * @date 2018/10/23
 */
@Module(includes = [NetworkModule::class])
class AppModule(private val application: Application) {

    @Provides
    internal fun provideApplication(): Application = application
}