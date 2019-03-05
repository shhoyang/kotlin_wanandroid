package com.hao.easy.wan.di

import android.app.Activity
import android.support.v4.app.Fragment
import com.hao.easy.base.App
import com.hao.easy.wan.di.component.ActivityComponent
import com.hao.easy.wan.di.component.DaggerActivityComponent
import com.hao.easy.wan.di.component.DaggerFragmentComponent
import com.hao.easy.wan.di.component.FragmentComponent

fun Activity.component(): ActivityComponent =
        DaggerActivityComponent.builder()
                .appComponent(App.instance.appComponent)
                .build()

fun Fragment.component(): FragmentComponent =
        DaggerFragmentComponent.builder()
                .appComponent(App.instance.appComponent)
                .build()
