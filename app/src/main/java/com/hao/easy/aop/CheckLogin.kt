package com.hao.easy.aop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.hao.easy.Config
import com.hao.easy.base.common.AppManager
import com.hao.easy.ui.activity.LoginActivity
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature


/**
 * @author Yang Shihao
 * @Date 2020/6/20
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CheckLogin

@Aspect
class CheckLoginAspect {

    @Pointcut("execution(@com.hao.easy.aop.CheckLogin * *(..))")
    fun pointcutLogin() {

    }

    @Around("pointcutLogin()")
    @Throws(Throwable::class)
    fun aroundLogin(joinPoint: ProceedingJoinPoint) {
        val signature = joinPoint.signature as MethodSignature
        val annotation = signature.method.getAnnotation(CheckLogin::class.java)
        if (annotation != null) {
            if (Config.isLogin) {
                joinPoint.proceed()
            } else {
                AppManager.instance().getLastActivity()?.apply {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
    }

    private fun getContext(obj: Any): Context? {
        return when (obj) {
            is Activity -> obj
            is Fragment -> obj.activity
            is View -> obj.context
            else -> null
        }
    }
}