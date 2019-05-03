package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectView {
    private String clName;
    private List<String> methodList = new ArrayList<String>();
    private List<String> methodListFull = new ArrayList<String>();
    private List<String> fieldList = new ArrayList<String>();
    Method invokeMethod;

    private static Object gameClass = null;
    @FXML
    private Label getterFieldsNames;
    @FXML
    private Label setterFieldsNames;
    @FXML
    private Label methodsFieldsNames;
    @FXML
    private TextField fieldName;
    @FXML
    private Label invokedMethodLabel;
    @FXML
    private TextField value;
    @FXML
    private TextField methodName;
    @FXML
    private Label fieldInfo;

    public void initialize(){
        clName=ClassView.className;
        createClassObject(clName);
        getAllFields();
        setMethodView();
        setGetterFields();
        setSetterFields();
    }

    @FXML
    void fillField(ActionEvent event) {
        if(fieldName.getText().equals("")||value.getText().equals("")){
            fieldInfo.setText("Field name or value are empty! Fill empty fields!");
        }else{
            Method method;
            String setterName=takeSetter(fieldName.getText());
            try {
                method=gameClass.getClass().getMethod(setterName);
                method.invoke(gameClass);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //gameClass.getClass().
//            System.out.println(takeSetter(fieldName.getText()));
        }
        setGetterFields();

    }
    String takeSetter(String field){
        String setterName="";
        for(String f: methodListFull){
            if(("set"+field).toLowerCase().equals(f.toLowerCase())) setterName = f;
        }
        return setterName;
    }

    @FXML
    void invokeMethod(ActionEvent event) {
        if(methodName.getText().equals("")){
            invokedMethodLabel.setText("Fill in method name!");
        }else if(checkMethodExists(methodName.getText())==true){
        try {
            invokeMethod = gameClass.getClass().getMethod(methodName.getText());
            String result = invokeMethod.invoke(gameClass).toString();
            invokedMethodLabel.setText(result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        }else{
            invokedMethodLabel.setText("Method with this name does not exist in this class!");
        }
        setGetterFields();
    }

    boolean checkMethodExists(String methodName){
        boolean exists = false;
        for(String m: methodList){
            if(m.equals(methodName)){
                exists= true;
                break;
            }
            else{
                exists =false;
            }
        }
        return exists;
    }

    void createClassObject(String ClassName){
        try{
            gameClass = Class.forName(clName).newInstance();
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    void getAllMethods(){
        Method[] method = gameClass.getClass().getDeclaredMethods();
        for(Method m:method){
            if(!(m.getName().startsWith("get")||m.getName().startsWith("set"))){
                methodList.add(m.getName());
            }
            methodListFull.add(m.getName());
        }
    }

    void getAllFields(){
        Field[] fields =gameClass.getClass().getDeclaredFields();
        for(Field f: fields){
            fieldList.add(f.getName());
        }
    }

    void setMethodView(){
        getAllMethods();
        String methods="";

        for(String s:methodList){
            methods += s + ", ";
        }
        methodsFieldsNames.setText(methods);
    }

    void setGetterFields(){
        Method[] methods = gameClass.getClass().getDeclaredMethods();
        Field[] fields = gameClass.getClass().getDeclaredFields();
        String methGet="";
        for(Method method : methods){
            if(isGetter(method))
                for(Field field: fields){
                    String f="get"+field.getName().toLowerCase();
                    if(method.getName().toLowerCase().equals(f)){
                        methGet+="Field name: "+ field.getName() + " Field type: " + field.getType().toString()+
                                " Value: "+getValue(field.getName())+"\n";
                    }
                }
        }
        getterFieldsNames.setText(methGet);
    }

    String getValue(String fieldName){
        String getterName="";
        for(String f: methodListFull){
            if(("get"+fieldName).toLowerCase().equals(f.toLowerCase())) getterName = f;
        }
        Method method;
        String val="";
        try {
            method=gameClass.getClass().getMethod(getterName);
            val = String.valueOf(method.invoke(gameClass));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return val;
    }
    void setSetterFields(){
        Method[] methods = gameClass.getClass().getDeclaredMethods();
        Field[] fields = gameClass.getClass().getDeclaredFields();
        String methSet="";
        for(Method method : methods){
            if(isSetter(method)){
                for(Field field: fields){
                    String f="set"+field.getName().toLowerCase();
                    if(method.getName().toLowerCase().equals(f)){
                        methSet+="Field name: " + field.getName() + " Field type: " + field.getType().toString()+"\n";
                    }
                }
            }
        }
        setterFieldsNames.setText(methSet);
    }

    public static boolean isGetter(Method method){
        if(!method.getName().startsWith("get"))      return false;
        if(method.getParameterTypes().length != 0)   return false;
        if(void.class.equals(method.getReturnType())) return false;
        return true;
    }

    public static boolean isSetter(Method method){
        if(!method.getName().startsWith("set")) return false;
        if(method.getParameterTypes().length != 1) return false;
        return true;
    }
}
