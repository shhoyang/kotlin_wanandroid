package com.hao.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yang Shihao
 * <p>
 * 注解BaseActivity或者BaseFragment，向父类查找被注解字段的时候使用
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Base {

}
