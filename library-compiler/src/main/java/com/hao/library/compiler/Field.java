package com.hao.library.compiler;

/**
 * @author Yang Shihao
 */
class Field {

    private String annotationType;
    private String filedType;
    private String filedName;

    protected Field(String annotationType, String filedType, String filedName) {
        this.annotationType = annotationType;
        this.filedType = filedType;
        this.filedName = filedName;
    }

    protected String getAnnotationType() {
        return annotationType;
    }

    protected String getFiledType() {
        return filedType;
    }

    protected void setFiledType(String filedType) {
        this.filedType = filedType;
    }

    protected String getFiledName() {
        return filedName;
    }
}
