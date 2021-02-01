package com.hao.library.annotation;

/**
 * @author Yang Shihao
 */
public class DaggerConstant {
    /**
     * 生成的类是原类名加上这个后缀
     */
    public static final String SUFFIX = "Injector";
    /**
     * 生成类的静态方法的名字
     */
    public static final String STATIC_METHOD_NAME = "inject";

    /**
     * option，ViewBinding的特征key
     */
    public static final String KEY_VIEW_BINDING_FEATURE = "DAGGER_VIEW_BINDING_FEATURE";

    /**
     * option，ViewModel的特征key
     */
    public static final String KEY_VIEW_MODEL_FEATURE = "DAGGER_VIEW_MODEL_FEATURE";

    /**
     * option，Adapter的特征key
     */
    public static final String KEY_ADAPTER_FEATURE = "DAGGER_ADAPTER_FEATURE";

}
