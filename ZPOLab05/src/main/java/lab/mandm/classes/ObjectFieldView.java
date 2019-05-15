package lab.mandm.classes;

import java.lang.reflect.Type;

public class ObjectFieldView {
    private String name;
    private String fieldName;
    private String  fieldType;
    private Object fieldValue;

    public ObjectFieldView(String name, String fieldName, String fieldType, Object fieldValue) {
        this.name = name;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public String toString() {
        return name;
    }
}