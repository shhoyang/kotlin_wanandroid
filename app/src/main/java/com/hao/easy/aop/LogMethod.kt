package com.hao.easy.aop

import com.socks.library.KLog
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import java.util.*


/**
 * @author Yang Shihao
 * @Date 2020/6/20
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogMethod

@Aspect
class LogMethodAspect {

    @Pointcut("execution(@com.hao.easy.aop.LogMethod * *(..))")
    fun pointcutLogMethod() {

    }

    @Around("pointcutLogMethod()")
    @Throws(Throwable::class)
    fun aroundLogMethod(joinPoint: ProceedingJoinPoint): Any? {
        return printLog(joinPoint)
    }

    private fun printLog(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature

        val stackTraceElement = getStackTraceElement(
            Thread.currentThread().stackTrace,
            signature.declaringType.name
        )

        // 类名
        val className = if (null == stackTraceElement) {
            signature.declaringType.simpleName
        } else {
            "(${stackTraceElement.fileName}:${stackTraceElement.lineNumber})"
        }

        // 参数
        val args = joinPoint.args
        val argsString = if (null == args || args.isEmpty()) {
            "[]"
        } else {
            Arrays.deepToString(args)
        }

        // 返回值
        val result = joinPoint.proceed(args)
        val resultString = if ("void" == signature.returnType.toString()) {
            "void"
        } else {
            result
        }

        KLog.json(
            "LogMethod",
            "{\"className\":\"${className}\",\"methodName\":${signature.name},\"args\":${argsString},\"result\":${resultString}}"
        )

        return result
    }

    private fun getStackTraceElement(
        stackTrace: Array<StackTraceElement>,
        className: String
    ): StackTraceElement? {
        stackTrace.forEach {
            if (it.className == className) {
                return it
            }
        }

        return null
    }
}