package com.hao.library.view.web

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.widget.ProgressBar
import com.hao.library.extensions.gone

class ProgressAnimHelper(val progressBar: ProgressBar) {

    private var progressAnimator: ObjectAnimator? = null
    private var dismissAnimator: ObjectAnimator? = null

    private var isCompleted = false

    fun progressChanged(newProgress: Int) {
        if (!isCompleted) {
            var temp = newProgress
            if (temp >= 100) {
                temp = 100
                isCompleted = true
            }
            startProgressAnim(temp)
        }
    }

    fun destroy() {
        progressAnimator?.cancel()
        dismissAnimator?.cancel()
    }

    private fun startProgressAnim(newProgress: Int) {
        progressAnimator?.cancel()
        progressAnimator =
            ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, newProgress)
        progressAnimator!!.apply {
            duration = 300
            if (newProgress >= 100) {
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        startDismissAnim()
                    }
                })
            }
            start()
        }
    }


    private fun startDismissAnim() {
        dismissAnimator = ObjectAnimator.ofFloat(progressBar, "alpha", 1F, 0F)
        dismissAnimator!!.apply {
            duration = 300
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    progressBar.gone()
                }
            })
            start()
        }
    }
}
