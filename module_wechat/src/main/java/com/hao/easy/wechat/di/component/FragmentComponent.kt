package com.hao.easy.wechat.di.component

import com.hao.easy.base.di.component.AppComponent
import com.hao.easy.inject.FragmentScope
import com.hao.easy.wechat.ui.fragment.FlutterFragment
import com.hao.easy.wechat.ui.fragment.KotlinFragment
import com.hao.easy.wechat.ui.fragment.ProjectFragment
import com.hao.easy.wechat.ui.fragment.WechatArticleFragment
import dagger.Component


/**
 * @author Yang Shihao
 * @date 2018/12/7
 */

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface FragmentComponent {

    fun inject(wechatArticleFragment: WechatArticleFragment)

    fun inject(projectFragment: ProjectFragment)

    fun inject(kotlinFragment: KotlinFragment)

    fun inject(flutterFragment: FlutterFragment)
}