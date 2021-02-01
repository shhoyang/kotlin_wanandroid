package com.hao.easy.user

import android.content.Intent
import com.hao.easy.user.fragment.UserFragment
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.databinding.ActivityContainerBinding
import com.hao.library.ui.ContainerActivity
import com.hao.library.ui.UIParams

@AndroidEntryPoint(injectViewModel = false)
class MainActivity : ContainerActivity<ActivityContainerBinding>() {

    override fun prepare(uiParams: UIParams, intent: Intent?) {
        uiParams.isTransparentStatusBar = true
    }

    override fun getFragment() = UserFragment()
}

