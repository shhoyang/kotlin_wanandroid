package com.hao.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yang Shihao
 * <p>
 * 注解类，被标记的类会生成对应的注入类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface AndroidEntryPoint {

    /**
     * 是否注入ViewModel，有些类不需要ViewModel，设置为false
     */
    boolean injectViewModel() default true;
}
