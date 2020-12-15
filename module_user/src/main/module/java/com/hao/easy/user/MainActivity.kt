package com.hao.easy.user

import android.content.Intent
import com.hao.easy.base.ui.ContainerActivity
import com.hao.easy.base.ui.UIParams
import com.hao.easy.user.ui.fragment.UserFragment

class MainActivity : ContainerActivity() {

    override fun prepare(uiParams: UIParams, intent: Intent?) {
        uiParams.isTransparentStatusBar = true
    }

    override fun getFragment() = UserFragment()
}

