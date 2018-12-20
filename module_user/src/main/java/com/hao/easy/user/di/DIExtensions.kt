package com.hao.easy.user.di

import android.app.Activity
import android.support.v4.app.Fragment
import com.hao.easy.base.App
import com.hao.easy.user.di.component.ActivityComponent
import com.hao.easy.user.di.component.DaggerActivityComponent
import com.hao.easy.user.di.component.DaggerFragmentComponent
import com.hao.easy.user.di.component.FragmentComponent

/**
 * @author Yang Shihao
 * @date 2018/12/9
 */

fun Activity.component(): ActivityComponent =
        DaggerActivityComponent.builder()
                .appComponent(App.instance.appComponent)
                .build()

fun Fragment.component(): FragmentComponent =
        DaggerFragmentComponent.builder()
                .appComponent(App.instance.appComponent)
                .build()