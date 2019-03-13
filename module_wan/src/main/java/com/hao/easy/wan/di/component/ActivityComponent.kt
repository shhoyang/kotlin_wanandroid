package com.hao.easy.wan.di.component

import com.hao.easy.base.di.component.AppComponent
import com.hao.easy.base.di.scope.ActivityScope
import com.hao.easy.wan.ui.activity.FavActivity
import com.hao.easy.wan.ui.activity.KnowledgeActivity
import com.hao.easy.wan.ui.activity.KnowledgeArticleActivity
import com.hao.easy.wan.ui.activity.ProjectArticleActivity
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