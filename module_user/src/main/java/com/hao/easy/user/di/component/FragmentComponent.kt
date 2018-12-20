package com.hao.easy.user.di.component

import com.hao.easy.base.di.component.AppComponent
import com.hao.easy.inject.ActivityScope
import com.hao.easy.inject.FragmentScope
import com.hao.easy.user.ui.activity.LoginActivity
import com.hao.easy.user.ui.activity.WelcomeActivity
import com.hao.easy.user.ui.fragment.LoginFragment
import com.hao.easy.user.ui.fragment.UserFragment
import dagger.Component


/**
 * @author Yang Shihao
 * @date 2018/12/7
 */

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface FragmentComponent {

}