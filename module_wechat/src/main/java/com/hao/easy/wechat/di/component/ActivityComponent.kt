package com.hao.easy.wechat.di.component

import com.hao.easy.base.di.component.AppComponent
import com.hao.easy.inject.ActivityScope
import com.hao.easy.wechat.ui.activity.FavActivity
import com.hao.easy.wechat.ui.activity.KnowledgeActivity
import com.hao.easy.wechat.ui.activity.KnowledgeArticleActivity
import com.hao.easy.wechat.ui.activity.ProjectArticleActivity
import dagger.Component


/**
 * @author Yang Shihao
 * @date 2018/12/7
 */

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {

    fun inject(projectArticleActivity: ProjectArticleActivity)

    fun inject(favActivity: FavActivity)

    fun inject(knowledgeActivity: KnowledgeActivity)

    fun inject(knowledgeArticleActivity: KnowledgeArticleActivity)
}