package com.hao.library.extensions

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @author Yang Shihao
 */

/**
 * 手机号
 */
fun String.isPhone(): Boolean {
    return matches("^1[3-9]\\d{9}$".toRegex())
}

/**
 * 微信号
 */
fun String.isWechat(): Boolean {
    return matches("[-_a-zA-Z0-9]{1,20}".toRegex())
}

/**
 * 是否是指定长度的纯数字
 */
fun String.isInteger(length: Int): Boolean {
    return matches(("^\\d{$length}$").toRegex())
}

/**
 * 验证码
 */
fun String.isNotVerificationCode(): Boolean {
    return !matches("^\\d{4,6}$".toRegex())
}

fun String.removeSymbol(): String {
    return replace(Regex("(&[^;]+;)|(<[^>]+>)"), "")
}

fun String.removeHtml(): String {
    return replace(Regex("<[^>]+>"), "")
}

fun String.isNotChinese(): Boolean {
    return !matches("[\u4e00-\u9fa5]+".toRegex())
}


/**
 * MD5加密
 */
fun String.md5(): String {
    try {
        val sb = StringBuffer()
        val md5 = MessageDigest.getInstance("MD5")
        val digest = md5.digest(this.toByteArray())
        for (b in digest) {
            val i: Int = b.toInt() and 0xff
            var hexString = Integer.toHexString(i)
            if (hexString.length == 1) {
                hexString = "0$hexString"
            }
            sb.append(hexString)
        }
        return sb.toString()

    } catch (e: NoSuchAlgorithmException) {
    }

    return ""
}




