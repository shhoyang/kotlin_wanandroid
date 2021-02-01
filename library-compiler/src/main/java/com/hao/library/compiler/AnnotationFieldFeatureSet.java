package com.hao.library.compiler;


import com.hao.library.annotation.DaggerAndroidApp;
import com.hao.library.annotation.DaggerConstant;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * @author Yang Shihao
 */
class AnnotationFieldFeatureSet {

    private final Set<String> viewBindingFeatureSet;
    private final Set<String> viewModelFeatureSet;
    private final Set<String> adapterFeatureSet;

    protected AnnotationFieldFeatureSet(RoundEnvironment roundEnv, Map<String, String> options) {
        viewBindingFeatureSet = new HashSet<>();
        viewModelFeatureSet = new HashSet<>();
        adapterFeatureSet = new HashSet<>();
        getFeature(roundEnv, options);
    }

    private void getFeature(RoundEnvironment roundEnv, Map<String, String> options) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(DaggerAndroidApp.class);
        for (Element element : elements) {
            DaggerAndroidApp daggerAndroidApp = element.getAnnotation(DaggerAndroidApp.class);
            if (daggerAndroidApp != null) {
                String[] viewBindingFeatures = daggerAndroidApp.viewBindingFeatures();
                arrayAddToSet(viewBindingFeatureSet, viewBindingFeatures);

                String[] viewModelFeatures = daggerAndroidApp.viewModelFeatures();
                arrayAddToSet(viewModelFeatureSet, viewModelFeatures);

                String[] adapterFeatures = daggerAndroidApp.adapterFeatures();
                arrayAddToSet(adapterFeatureSet, adapterFeatures);
            }
        }

        String viewBindingFeature = options.get(DaggerConstant.KEY_VIEW_BINDING_FEATURE);
        if (Utils.isNotEmpty(viewBindingFeature)) {
            viewBindingFeatureSet.add(viewBindingFeature);
        }

        String viewModelFeature = options.get(DaggerConstant.KEY_VIEW_MODEL_FEATURE);
        if (Utils.isNotEmpty(viewModelFeature)) {
            viewModelFeatureSet.add(viewModelFeature);
        }

        String adapterFeature = options.get(DaggerConstant.KEY_ADAPTER_FEATURE);
        if (Utils.isNotEmpty(adapterFeature)) {
            adapterFeatureSet.add(adapterFeature);
        }
    }

    private void arrayAddToSet(Set<String> set, String[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        for (String s : array) {
            set.add(s.toLowerCase());
        }
    }

    protected boolean isViewBinding(String qualifiedClassName) {
        String s = qualifiedClassName.toLowerCase();
        for (String feature : viewBindingFeatureSet) {
            if (s.contains(feature)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isViewModel(String qualifiedClassName) {
        String s = qualifiedClassName.toLowerCase();
        for (String feature : viewModelFeatureSet) {
            if (s.contains(feature)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isAdapter(String qualifiedClassName) {
        String s = qualifiedClassName.toLowerCase();
        for (String feature : adapterFeatureSet) {
            if (s.contains(feature)) {
                return true;
            }
        }
        return false;
    }
}
