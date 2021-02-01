package com.hao.library.aop

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.hao.library.HaoLibrary
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import kotlin.jvm.Throws

/**
 * @author Yang Shihao
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CheckLogin

@Aspect
class CheckLoginAspect {

    @Pointcut("execution(@com.hao.library.aop.CheckLogin * *(..))")
    fun pointcutLogin() {

    }

    @Around("pointcutLogin()")
    @Throws(Throwable::class)
    fun aroundLogin(joinPoint: ProceedingJoinPoint) {
        val signature = joinPoint.signature as MethodSignature
        val annotation = signature.method.getAnnotation(CheckLogin::class.java)
        if (annotation != null) {
            if (HaoLibrary.CONFIG.isLogin()) {
                joinPoint.proceed()
            } else {
                HaoLibrary.CONFIG.login()
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