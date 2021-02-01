package com.hao.library.compiler;

import com.hao.library.annotation.AndroidEntryPoint;
import com.hao.library.annotation.Base;
import com.hao.library.annotation.DaggerConstant;
import com.hao.library.annotation.Inject;
import com.hao.library.annotation.InjectViewBinding;
import com.hao.library.annotation.InjectViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author Yang Shihao
 */
class AnnotationMeta {

    /**
     * 提取泛型
     */
    private static final String PARAMETER_REGEX = "<(.+?)>";

    /**
     * 生成类的静态方法的参数名字
     */
    protected static final String PARAMS_NAME_ACTIVITY = "activity";
    /**
     * 包名
     */
    private final String packageName;
    /**
     * 注解类的类名
     */
    private final String annotatedClassName;
    /**
     * 生成类的类名
     */
    private final String generateClassName;
    /**
     * 支持的字段注解
     */
    private final Set<String> supportFiledAnnotation;
    /**
     * 扫描到的字段
     */
    private final List<Field> fieldList;

    private final Types typeUtils;
    private final TypeElement typeElement;
    private final AnnotationFieldFeatureSet fieldFeatureSet;
    private boolean injectViewModel = true;

    protected AnnotationMeta(Elements elementUtils, Types typeUtils, AnnotationFieldFeatureSet fieldFeatureSet, TypeElement typeElement) {
        this.typeUtils = typeUtils;
        this.typeElement = typeElement;
        this.fieldFeatureSet = fieldFeatureSet;
        packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        annotatedClassName = typeElement.getSimpleName().toString();
        generateClassName = annotatedClassName + DaggerConstant.SUFFIX;
        supportFiledAnnotation = new HashSet<>(4);
        fieldList = new ArrayList<>();
        scanField();
    }

    private void scanField() {
        supportFiledAnnotation.add(InjectViewBinding.class.getCanonicalName());
        supportFiledAnnotation.add(Inject.class.getCanonicalName());
        injectViewModel = typeElement.getAnnotation(AndroidEntryPoint.class).injectViewModel();
        if (injectViewModel) {
            supportFiledAnnotation.add(InjectViewModel.class.getCanonicalName());
        }
        Map<String, Field> superclassAnnotatedFieldMap = new HashMap<>(8);
        scanSuperclassAnnotatedField(typeUtils.asElement(this.typeElement.getSuperclass()), superclassAnnotatedFieldMap);
        getSuperclassAnnotatedFieldType(superclassAnnotatedFieldMap);
        scanCurrentClassAnnotatedField();
    }

    /**
     * 获取父类中被注解的成员变量
     */
    private void scanSuperclassAnnotatedField(Element element, Map<String, Field> map) {
        List<? extends Element> elements = element.getEnclosedElements();
        for (Element e : elements) {
            if (e.getKind() == ElementKind.FIELD) {
                for (AnnotationMirror a : e.getAnnotationMirrors()) {
                    if (supportFiledAnnotation.contains(a.getAnnotationType().toString())) {
                        map.put(a.getAnnotationType().toString(),
                                new Field(a.getAnnotationType().toString(), "", e.getSimpleName().toString())
                        );
                        break;
                    }
                }
            }
        }
        if (element instanceof TypeElement && element.getAnnotation(Base.class) != null) {
            scanSuperclassAnnotatedField(typeUtils.asElement(((TypeElement) element).getSuperclass()), map);
        }
    }

    /**
     * 获取父类中被注解的成员变量的类型
     */
    private void getSuperclassAnnotatedFieldType(Map<String, Field> map) {
        Map<String, String> fieldTypeMap = new HashMap<>(4);


        String[] types = getSuperclassTypeArguments();
        for (String type : types) {
            String key = null;

            if (fieldFeatureSet.isViewBinding(type)) {
                key = InjectViewBinding.class.getCanonicalName();
            } else if (fieldFeatureSet.isViewModel(type)) {
                key = InjectViewModel.class.getCanonicalName();
            } else if (fieldFeatureSet.isAdapter(type)) {
                key = Inject.class.getCanonicalName();
            }

            if (key == null) {
                continue;
            }

            Field field = map.get(key);
            if (field == null) {
                continue;
            }

            field.setFiledType(type);
        }

        map.forEach((key, field) -> {
            if (Utils.isNotEmpty(field.getFiledType())) {
                fieldList.add(field);
            }
        });
    }

    /**
     * 获取当前类中被注解的成员变量
     */
    private void scanCurrentClassAnnotatedField() {
        List<? extends Element> elements = typeElement.getEnclosedElements();
        for (Element e : elements) {
            if (e.getKind() == ElementKind.FIELD) {
                for (AnnotationMirror a : e.getAnnotationMirrors()) {
                    if (supportFiledAnnotation.contains(a.getAnnotationType().toString())) {
                        fieldList.add(
                                new Field(a.getAnnotationType().toString(), e.asType().toString(), e.getSimpleName().toString())
                        );
                        break;
                    }
                }
            }
        }
    }

    private String[] getSuperclassTypeArguments() {
        Matcher matcher = Pattern.compile(PARAMETER_REGEX).matcher(typeElement.getSuperclass().toString());
        if (matcher.find()) {
            String typeParameter = matcher.group(1);
            return typeParameter.split(",");
        }
        return new String[0];
    }

    protected String getPackageName() {
        return packageName;
    }

    protected String getAnnotatedClassName() {
        return annotatedClassName;
    }

    protected String getGenerateClassName() {
        return generateClassName;
    }

    protected List<Field> getFieldList() {
        return fieldList;
    }
}
