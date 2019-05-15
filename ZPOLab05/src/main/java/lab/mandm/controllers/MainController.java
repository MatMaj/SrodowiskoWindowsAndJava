package lab.mandm.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lab.mandm.classes.ClassFinder;
import lab.mandm.classes.ClassView;

import java.io.IOException;
import java.util.ArrayList;


public class MainController {
    private ArrayList<ClassView> classes = new ArrayList<>();
    private final String packageName = "lab.mandm.beanClasses";
    private ClassFinder classFinder = new ClassFinder(packageName);


    @FXML
    public void initialize() {
        classes = classFinder.getClassViews();

        addClassesToComboBox(classesComboBox);
    }

    @FXML
    private ComboBox<ClassView> classesComboBox;
    @FXML
    private Label labelItIs;

    @FXML
    void selectClass(ActionEvent event) {
        ClassView classView = classesComboBox.getSelectionModel().getSelectedItem();
        createObjectView(classView);
        Stage stage = (Stage) labelItIs.getScene().getWindow();
        stage.close();
    }


    private void addClassesToComboBox(ComboBox comboBox) {
        ObservableList<ClassView> classesObservableList = FXCollections.observableList(classes);
        comboBox.setItems(classesObservableList);
        comboBox.getSelectionModel().selectFirst();
    }

    private void createObjectView(ClassView classView) {
        try {
            Stage objectStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ObjectView.fxml"));
            Parent rootObject = fxmlLoader.load();
            objectStage.setTitle("Class - " + classView.toString());
            objectStage.setScene(new Scene(rootObject));

            ObjectController objectController = fxmlLoader.getController();
            objectController.setupClassThings(classView.getClazz());
            objectStage.getScene().getStylesheets().add("/style.css");
            objectStage.setResizable(false);
            objectStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
