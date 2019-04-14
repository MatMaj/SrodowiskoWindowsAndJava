package controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppController {
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5400));

    @FXML
    public void initialize() {
        fadeOutSetUp();
        loginPasswordTextField.setVisible(false);
    }
    private void fadeOutSetUp(){
        fadeOut.setNode(infoLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
    }
    @FXML
    private TextField loginLoginField;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private TextField loginPasswordTextField;
    @FXML
    private CheckBox loginShowPassword;
    @FXML
    private Button logon;
    @FXML
    private Label infoLabel;
    @FXML
    private TextField regNameField;
    @FXML
    private TextField regSurnameField;
    @FXML
    private TextField regEmailField;
    @FXML
    private TextField regLoginField;
    @FXML
    private TextField regPasswordField;
    @FXML
    private TextField regRepeatPasswordField;
    @FXML
    void showPassword(MouseEvent event) {
        if (loginShowPassword.isSelected()) {
            String passwordF = loginPasswordField.getText();
            loginPasswordField.setVisible(false);

            loginPasswordTextField.setText(passwordF);
            loginPasswordTextField.setVisible(true);
        } else if (!loginShowPassword.isSelected()) {
            String passwordTF = loginPasswordTextField.getText();
            loginPasswordTextField.setVisible(false);

            loginPasswordField.setText(passwordTF);
            loginPasswordField.setVisible(true);
        }
    }
    private String log;
    private String pass;
    private String rights;
    private int counter=0;
    @FXML
    void loginLogin(ActionEvent event) {
        if(loginShowPassword.isSelected()){
            log = loginPasswordTextField.getText();
        }else{
            log = loginPasswordField.getText();
        }
        if(loginLoginField.getText().equals("") || log.equals("")){
            addToInfoLabel("Błąd podczas logowania - puste pole login lub hasło", Color.RED);
        }else{
            try {
                dbconn();
                resultSet = statement.executeQuery("select * from userdatabase where login like '"+loginLoginField.getText()+"'");
                while (resultSet.next()) {
                    pass=resultSet.getString("haslo");
                    rights=resultSet.getString("uprawnienia");
                }
                if(log.equals(pass)){
                    addToInfoLabel("Login Succesfull", Color.GREEN);
                    if(rights.equals("Administrator")){
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminView.fxml"));
                            Parent root2 = fxmlLoader.load();
                            Stage aboutStage = new Stage();
                            aboutStage.setTitle("Admin");
                            aboutStage.setScene(new Scene(root2,640,480));
                            aboutStage.show();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if(rights.equals("User")){
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/userView.fxml"));
                            Parent root2 = fxmlLoader.load();
                            Stage aboutStage = new Stage();
                            aboutStage.setTitle("User");
                            aboutStage.setScene(new Scene(root2, 640, 480));
                            aboutStage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        addToInfoLabel("Twoje konto nie ma dostępu do aplikacji", Color.RED);
                        counter++;
                    }
                }else{
                    addToInfoLabel("Błędne hasło lub login", Color.RED);
                    counter++;
                }
                if(counter==3){
                    logon.setDisable(true);
                    addToInfoLabel("Trzykrotnie źle wpisane hasło - blokada logowania", Color.RED);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void checkRepeatPassword(KeyEvent event) {
        String password = regPasswordField.getText();
        String repeatPassword = regRepeatPasswordField.getText();

        if(!repeatPassword.equals(password)){
            addToInfoLabel("Hasło się nie zgadza", Color.RED);
            regRepeatPasswordField.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
        } else {
            endInfoLabelFadeOut();
            regRepeatPasswordField.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
        }
    }
    @FXML
    void checkPassword(KeyEvent event) {
        checkRepeatPassword(null);
    }
    @FXML
    void checkEmail(KeyEvent event) {
        String email = regEmailField.getText();
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches() && !email.equals("")){
            addToInfoLabel("Email się nie zgadza", Color.RED);
            regEmailField.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
        } else {
            endInfoLabelFadeOut();
            regEmailField.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
        }
    }
    void addToInfoLabel(String text, Color color){
        infoLabel.setText(text);
        infoLabel.setTextFill(color);
        infoLabel.setVisible(true);
        fadeOut.playFromStart();
    }
    void endInfoLabelFadeOut(){
        infoLabel.setVisible(false);
    }

    private Connection connector;
    private Statement statement;
    private ResultSet resultSet;
    public void dbconn(){
        try{
            connector = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginapp?useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");
            statement = connector.createStatement();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
