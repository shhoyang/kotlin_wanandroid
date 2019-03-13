package com.hao.easy.user.di.component

import com.hao.easy.base.di.component.AppComponent
import com.hao.easy.base.di.scope.ActivityScope
import com.hao.easy.user.ui.activity.AboutActivity
import dagger.Component


/**
 * @author Yang Shihao
 * @date 2018/12/7
 */

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {
    fun inject(aboutActivity: AboutActivity)
}