package com.hao.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yang Shihao
 * <p>
 * 获取项目的ViewBinding、ViewModel和Adapter的全类型特征
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface DaggerAndroidApp {

    /**
     * ViewBinding的特征
     *
     * @return ViewBinding的特征
     */
    String[] viewBindingFeatures();

    /**
     * ViewModel的特征
     *
     * @return ViewModel的特征
     */
    String[] viewModelFeatures();

    /**
     * Adapter的特征
     *
     * @return adapter的特征
     */
    String[] adapterFeatures();
}
