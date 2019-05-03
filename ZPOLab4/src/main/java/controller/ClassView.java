package controller;

import classes.BoardGame;
import classes.CardGame;
import classes.PaperRPG;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClassView {
    public static String className;

    @FXML
    private Button showClassName;
    @FXML
    private Button createObjectName;
    @FXML
    private TextField inputClassName;
    @FXML
    private Label errorLabel;
    @FXML
    private Label classList;

    @FXML
    void showClass(ActionEvent event) {
        Class reflectBoardGame = BoardGame.class;
        Class reflectCardGame = CardGame.class;
        Class reflectPaperRPG = PaperRPG.class;
        classList.setText(reflectBoardGame.getName()+"\n"+reflectCardGame.getName()+"\n"+reflectPaperRPG.getName());
    }

    @FXML
    void createObject(ActionEvent event) {
        Class reflectBoardGame = BoardGame.class;
        Class reflectCardGame = CardGame.class;
        Class reflectPaperRPG = PaperRPG.class;
        if(inputClassName.getText().equals("")){
            setErrorLabel("Podaj nazwę klasy!");
        }else if(inputClassName.getText().equals(reflectBoardGame.getName()) || inputClassName.getText().equals(reflectCardGame.getName()) || inputClassName.getText().equals(reflectPaperRPG.getName())){
            setErrorLabel("Sukces!");
            className=inputClassName.getText();
            changeView();
        }else{
            setErrorLabel("Nie istnieje klasa o zadanej nazwie");
        }
    }

    void setErrorLabel(String errorText){
        errorLabel.setText(errorText);
    }

    void changeView(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/objectView.fxml"));
            Parent root2 = fxmlLoader.load();
            Stage aboutStage = new Stage();
            aboutStage.setTitle("Created Object");
            aboutStage.setScene(new Scene(root2,640,480));
            aboutStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
