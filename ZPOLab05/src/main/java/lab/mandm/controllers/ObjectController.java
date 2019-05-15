package lab.mandm.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lab.mandm.annotations.Named;
import lab.mandm.classes.BecauseAnnotation;
import lab.mandm.classes.ObjectFieldView;
import org.apache.commons.beanutils.ConvertUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectController {
    private Class<?> clazz;
    private Method setter;
    private Class<?> setterType;
    private boolean setterFlag = false;
    private boolean getValueFromEnumFlag = false;
    private boolean setterValueFlag = false;

    private Map<String, Object> objectMap = new HashMap<>();
    private ArrayList<String> objectNames = new ArrayList<>();
    private ArrayList<Method> setters = new ArrayList<>();
    private ArrayList<Method> getters = new ArrayList<>();
    private ArrayList<Field> fields = new ArrayList<>();
    private ArrayList<ObjectFieldView> objectFieldViews = new ArrayList<>();
    private ArrayList<Enum> enums = new ArrayList<>();
    private ObservableList<ObjectFieldView> objectFieldViewObservableList = FXCollections.observableList(objectFieldViews);
    private ObservableList<String> objectNamesObservableList = FXCollections.observableList(objectNames);
    private ObservableList<Method> settersObservableList = FXCollections.observableList(setters);
    private ObservableList<Method> gettersObservableList = FXCollections.observableList(getters);
    private ObservableList<Enum> enumObservableList = FXCollections.observableList(enums);

    private Alert alert;

    @FXML
    public void initialize() {
        setupTableView();
        setupComboBoxes();
        setupStupidNameCuzSomething();
    }

    private void setupTableView() {
        col_obj_name.prefWidthProperty().bind(objectView.widthProperty().divide(4.05));
        col_fld_type.prefWidthProperty().bind(objectView.widthProperty().divide(4.05));
        col_fld_name.prefWidthProperty().bind(objectView.widthProperty().divide(4.05));
        col_fld_value.prefWidthProperty().bind(objectView.widthProperty().divide(4.05));

        col_obj_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_fld_type.setCellValueFactory(new PropertyValueFactory<>("fieldType"));
        col_fld_name.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
        col_fld_value.setCellValueFactory(new PropertyValueFactory<>("fieldValue"));

        objectView.setItems(objectFieldViewObservableList);

    }

    private void setupComboBoxes() {
        objectsComboBox.setItems(objectNamesObservableList);
        settersComboBox.setItems(settersObservableList);
        gettersComboBox.setItems(gettersObservableList);
        enumsComboBox.setItems(enumObservableList);
    }

    @FXML
    private TableView<ObjectFieldView> objectView;
    @FXML
    private TableColumn<ObjectFieldView, String> col_obj_name;
    @FXML
    private TableColumn<ObjectFieldView, String> col_fld_type;
    @FXML
    private TableColumn<ObjectFieldView, String> col_fld_name;
    @FXML
    private TableColumn<ObjectFieldView, Object> col_fld_value;
    @FXML
    private ComboBox<String> objectsComboBox;
    @FXML
    private TextField objectNameTextField;
    @FXML
    private ComboBox<Method> gettersComboBox;
    @FXML
    private TextField valueGetTextField;
    @FXML
    private ComboBox<Method> settersComboBox;
    @FXML
    private TextField valueSetTextField;
    @FXML
    private ComboBox<Enum> enumsComboBox;

    @FXML
    void createObject(ActionEvent event) {
        String objectName = objectNameTextField.getText().trim();
        if (objectName.equals("") || !checkVar(objectName)) {
            alert = new Alert(Alert.AlertType.WARNING, "Podaj poprawną nazwę", ButtonType.OK);
            alert.showAndWait();
        } else if (checkObjectName(objectName)) {
            alert = new Alert(Alert.AlertType.WARNING, "Istnieje obiekt o takiej nazwie", ButtonType.OK);
            alert.showAndWait();
        } else {
            createObject(objectName);
        }
        objectNameTextField.setText("");
    }

    @FXML
    void createSelectObject(ActionEvent event) {
        String objectName = objectNameTextField.getText().trim();
        createObject((ActionEvent) null);
        if (checkObjectName(objectName) && checkVar(objectName))
            objectsComboBox.getSelectionModel().select(objectName);
    }


    @FXML
    void checkVariable(KeyEvent event) {
        String objectName = objectNameTextField.getText().trim();

        if (checkVar(objectName) || objectName.equals("")) {
            correctTextField(objectNameTextField);
        } else {
            wrongTextField(objectNameTextField);
        }
    }

    @FXML
    void checkSetter(KeyEvent event) {
        String value = valueSetTextField.getText();
        setterValueFlag = false;
        if (value.equals("")) {
            correctTextField(valueSetTextField);
        } else if (setterType.isInstance(LocalDate.now())) {
            if (checkDate(value)) {
                correctTextField(valueSetTextField);
                setterValueFlag = true;
            } else {
                wrongTextField(valueSetTextField);
            }
        } else if (setterFlag && checkType(value, setterType)) {
            correctTextField(valueSetTextField);
            setterValueFlag = true;
        } else {
            wrongTextField(valueSetTextField);
        }
        if (setterType.isEnum()) {
            setterValueFlag = true;
        }
    }

    @FXML
    void deleteObject(ActionEvent event) {
        String objectName = objectsComboBox.getSelectionModel().getSelectedItem();
        if (!checkObjectName(objectName)) {
            alert = new Alert(Alert.AlertType.ERROR, "Brak obiektu o takiej nazwie lub obiekt nie jest zaznaczony", ButtonType.OK);
            alert.showAndWait();
        } else {
            deleteObject(objectName);
        }
    }

    @FXML
    void getterSelected(ActionEvent event) {
        Method m = gettersComboBox.getSelectionModel().getSelectedItem();

        String objectName = objectsComboBox.getSelectionModel().getSelectedItem();
        if (objectName.equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "Zaden obiekt nie został zaznaczony", ButtonType.OK);
            alert.showAndWait();
        } else if (!checkObjectName(objectName)) {
            alert = new Alert(Alert.AlertType.ERROR, "Brak obiektu o takiej nazwie", ButtonType.OK);
            alert.showAndWait();
        } else {
            Object o = getObjectFromName(objectName).get();
            valueGetTextField.setText(invokeGetter(m, o));
        }

    }

    @FXML
    void setterActivator(ActionEvent event) {
        String objectName = objectsComboBox.getSelectionModel().isEmpty() ? "" : objectsComboBox.getSelectionModel().getSelectedItem();
        Object value = getValueFromEnumFlag ? enumsComboBox.getSelectionModel().getSelectedItem() : valueSetTextField.getText().trim();

        if (!setterFlag) {
            alert = new Alert(Alert.AlertType.ERROR, "Zaden setter nie został zaznaczony", ButtonType.OK);
            alert.showAndWait();
        } else if (objectName.equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "Zaden obiekt nie został zaznaczony", ButtonType.OK);
            alert.showAndWait();
        } else if (!checkObjectName(objectName)) {
            alert = new Alert(Alert.AlertType.ERROR, "Brak obiektu o takiej nazwie", ButtonType.OK);
            alert.showAndWait();
        } else if (value.toString().equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "Brak wartości do zmiany", ButtonType.OK);
            alert.showAndWait();
        } else if (!setterValueFlag) {
            alert = new Alert(Alert.AlertType.ERROR, "Wartość w złym formacie", ButtonType.OK);
            alert.showAndWait();
        } else {
            String fieldName = getFieldNameFromSetter(setter);
            if (setterType.isInstance(LocalDate.now())) {
                invokeSetter(setter, getObjectFromName(objectName).get(), LocalDate.parse(value.toString()));
            } else {
                invokeSetter(setter, getObjectFromName(objectName).get(), ConvertUtils.convert(value, setterType));
            }
            updateViewBySetter(objectName, fieldName, value);
            valueSetTextField.setText("");
        }
    }

    @FXML
    void setterSelected(ActionEvent event) {
        Method m = settersComboBox.getSelectionModel().getSelectedItem();
        setter = m;
        setterType = getTypeForSetter(m);
        setterFlag = true;
        if (setterType.isEnum()) {
            enumsComboBox.setVisible(true);
            valueSetTextField.setVisible(false);
            getValueFromEnumFlag = true;
        } else {
            enumsComboBox.setVisible(false);
            valueSetTextField.setVisible(true);
            getValueFromEnumFlag = false;
        }
        checkSetter(null);
    }

    public void setupClassThings(Class<?> clazz) {
        this.clazz = clazz;
        getSetters();
        getGetters();
        getFields();
    }

    private void createObject(String name) {
        try {
            Object object = Class.forName(clazz.getName()).newInstance();
            addObject(object, name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void addObject(Object object, String name) {
        try {
            for (Field f : fields) {
                ObjectFieldView field = new ObjectFieldView(name, f.getName(), f.getType().getSimpleName(), f.get(object));
                objectFieldViews.add(field);
            }
            objectMap.put(name, object);
            objectNamesObservableList.add(name);
            objectView.refresh();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    private void deleteObject(String name) {
        objectMap.remove(name);
        objectNamesObservableList.remove(name);
        ArrayList<ObjectFieldView> temp = cloneObjectFieldViewList(objectFieldViews);
        for (ObjectFieldView ofv : temp) {
            if (ofv.getName().equals(name)) {
                objectFieldViewObservableList.remove(ofv);
            }
        }
        objectView.refresh();
    }

    private void updateViewBySetter(String objectName, String fieldName, Object fieldValue) {
        ArrayList<ObjectFieldView> temp = cloneObjectFieldViewList(objectFieldViews);
        for (ObjectFieldView ofv : temp) {
            if (ofv.getName().equals(objectName) && ofv.getFieldName().equals(fieldName)) {
                ofv.setFieldValue(fieldValue.toString());
            }
        }
        objectFieldViews = cloneObjectFieldViewList(temp);
        objectView.refresh();
    }

    private void getSetters() {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("set") && m.getParameterTypes().length == 1) {
                setters.add(m);
            }
        }
    }

    private void getGetters() {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !void.class.equals(m.getReturnType())) {
                getters.add(m);
            }
        }
    }

    private void getFields() {
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            this.fields.add(f);
            if (f.getType().isEnum()){
                getEnums(f);
            }
        }
    }

    private void getEnums(Field field) {
        Object[] os = field.getType().getEnumConstants();
        for (Object o : os) {
            enums.add((Enum) o);
        }
        enumsComboBox.getSelectionModel().selectFirst();
    }

    private Class<?> getTypeForSetter(Method method) {
        Class<?>[] classes = method.getParameterTypes();
        return classes[0];
    }

    private Optional<Object> getObjectFromName(String name) {
        return Optional.of(objectMap.get(name));
    }

    private String getFieldNameFromSetter(Method m) {
        try {
            Class<?> clazz = m.getDeclaringClass();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                if(m.equals(pd.getWriteMethod()) || m.equals(pd.getReadMethod())) {
                    return pd.getName();
                }
            }
        }
        catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean checkDate(String date) {
        String regex = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    private boolean checkType(String value, Class<?> type) {
        Object o = ConvertUtils.convert(value, type);
        return o.toString().equals(value);
    }

    private boolean checkVar(String name) {
        String regex = "^[a-zA-Z_$][a-zA-Z_$0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        return  matcher.matches();
    }

    private boolean checkObjectName(String name) {
        return objectNames.contains(name);
    }

    private String invokeGetter(Method m, Object o) {
        String value = "";
        try {
            value =  m.invoke(o).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

    private void invokeSetter(Method m, Object o, Object v) {
        try {
            m.invoke(o,v);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<ObjectFieldView> cloneObjectFieldViewList(ArrayList<ObjectFieldView> al) {
        ArrayList<ObjectFieldView> clone = new ArrayList<>();
        al.forEach(o -> clone.add(o));
        return clone;
    }

    private void wrongTextField(TextField tf) {
        tf.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
    }

    private void correctTextField(TextField tf) {
        tf.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
    }

    private void setupStupidNameCuzSomething() {
        col_fld_name.setText(new BecauseAnnotation().getName());
    }

}
