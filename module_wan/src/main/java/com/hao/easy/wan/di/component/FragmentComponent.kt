package com.hao.easy.wan.di.component

import com.hao.easy.base.di.component.AppComponent
import com.hao.easy.base.di.scope.FragmentScope
import com.hao.easy.wan.ui.fragment.*
import dagger.Component


/**
 * @author Yang Shihao
 * @date 2018/12/7
 */

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface FragmentComponent {

    fun inject(WechatFragment: WechatFragment)

    fun inject(wechatArticleFragment: WechatArticleFragment)

    fun inject(projectFragment: ProjectFragment)

    fun inject(kotlinFragment: KotlinFragment)

    fun inject(flutterFragment: FlutterFragment)
}