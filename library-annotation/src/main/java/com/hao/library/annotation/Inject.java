package com.hao.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yang Shihao
 * <p>
 * 注解属性，该属性的class必须有无参的构造方法
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Inject {

}
