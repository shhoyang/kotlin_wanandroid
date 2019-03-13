package com.hao.easy.user.di.component

import com.hao.easy.base.di.component.AppComponent
import com.hao.easy.base.di.scope.FragmentScope
import dagger.Component


/**
 * @author Yang Shihao
 * @date 2018/12/7
 */

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface FragmentComponent