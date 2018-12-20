package com.hao.easy.user.di.component

import com.hao.easy.base.di.component.AppComponent
import com.hao.easy.inject.ActivityScope
import com.hao.easy.user.ui.activity.WelcomeActivity
import dagger.Component


/**
 * @author Yang Shihao
 * @date 2018/12/7
 */

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {

}