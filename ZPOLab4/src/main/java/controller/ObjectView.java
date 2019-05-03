package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ObjectView {
    private String clName;
    private List methodList;

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
    private TextField value;
    @FXML
    private TextField methodName;

    public void initialize(){
        clName=ClassView.className;
        System.out.println(clName);
        createClassObject(clName);
    }

    @FXML
    void fillField(ActionEvent event) {
    }

    @FXML
    void invokeMethod(ActionEvent event) {

    }
    @FXML
    void getFields(ActionEvent event) {
        getMethods();
        getterFieldsNames.setText(methodList.toString());
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

    void getMethods(){
        Method[] method = gameClass.getClass().getMethods();
        for(Method m:method){
            methodList.add(m.getName());
        }
    }
}
