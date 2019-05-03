package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectView {
    private String clName;
    private List<String> methodList = new ArrayList<String>();
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

    public void initialize(){
        clName=ClassView.className;
        createClassObject(clName);
        setMethodView();
        setGetterSetterFields();
    }

    @FXML
    void fillField(ActionEvent event) {
    }

    @FXML
    void invokeMethod(ActionEvent event) {
        if(methodName.getText().equals("")){
            invokedMethodLabel.setText("Fill in method name!");
        }else if(checkMethodExists(methodName.getText())==true){
        try {
            invokeMethod = gameClass.getClass().getMethod(methodName.getText());
            invokeMethod.invoke(gameClass);
            invokedMethodLabel.setText(invokeMethod.invoke(gameClass).toString());
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
        int i=0;
        for(Method m:method){
            if(!(m.getName().startsWith("get")||m.getName().startsWith("set"))){
                methodList.add(m.getName());
            }
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

    void setGetterSetterFields(){
        Method[] methods = gameClass.getClass().getDeclaredMethods();
        Field[] fields = gameClass.getClass().getDeclaredFields();
        String methGet="";
        String methSet="";
        for(Method method : methods){
            if(isGetter(method))
                for(Field field: fields){
                    String f="get"+field.getName().toLowerCase();
                    if(method.getName().toLowerCase().equals(f)){
                        methGet+="Field name: "+ field.getName() + " Field type: " + field.getType().toString()+"\n";
                    }
                }
            else if(isSetter(method)){
                for(Field field: fields){
                    String f="set"+field.getName().toLowerCase();
                    if(method.getName().toLowerCase().equals(f)){
                        methSet+="Field name: " + field.getName() + " Field type: " + field.getType().toString()+"\n";
                    }
                }
            }
        }
        getterFieldsNames.setText(methGet);
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
