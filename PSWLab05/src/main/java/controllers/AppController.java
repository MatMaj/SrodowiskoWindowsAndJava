package controllers;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import impls.UserDAOImpl;
import models.Mailer;
import models.User;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppController {
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5400));
    private boolean activeScene = false;
    private Mailer mailer = new Mailer("javawindowslab", "j@vawindows123", "javawindowslab@gmail.com");
    private UserDAOImpl userDAO = new UserDAOImpl();
    private int counter = 0;

    @FXML
    public void initialize() {
        setupFadeOut();
        checkConnection();
        loginPasswordTextField.setVisible(false);
    }

    private void setupFadeOut() {
        fadeOut.setNode(infoLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
    }

    private void checkConnection(){
        if (!userDAO.checkConnection()){
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.RED);
            disableLogin();
            disableRegister();
        }

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
    private Button loginLoginButton;
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
    private Button regRegButton;
    @FXML
    private Label infoLabel;

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

    @FXML
    void loginLogin(ActionEvent event) {
        String login;
        String password;
        if (!activeScene) {
            login = loginLoginField.getText();
            if (loginShowPassword.isSelected()) {
                password = loginPasswordTextField.getText();
            } else {
                password = loginPasswordField.getText();
            }

            if (login.equals("") || password.equals("")) {
                addToInfoLabel("Puste pole login lub hasło", Color.RED);
            } else {
                if (!userDAO.checkLogin(login)) {
                    addToInfoLabel("Błędne hasło lub login", Color.RED);
                    counter++;
                } else {
                    Optional<User> user = userDAO.loginUser(login, password);
                    if (!user.isPresent()) {
                        addToInfoLabel("Błędne hasło lub login", Color.RED);
                        counter++;
                    } else {
                        successfulLogin(user.get().getRights(), user.get().getName());
                        counter = 0;
                    }
                }
            }
        } else {
            addToInfoLabel("Istnieje aktywne okno z zalogowanym użytkownikiem", Color.DARKGOLDENROD);
        }

        if (counter == 3) {
            disableLogin();
            addToInfoLabel("Trzykrotnie źle wpisane hasło - blokada logowania", Color.DARKRED);
        }
    }

    private void successfulLogin(String rights, String name) {
        addToInfoLabel("Logowanie powiodło się! Witamy!", Color.GREEN);
        if (rights.equals("Administrator")) {
            createAdminView();
        } else if (rights.equals("User")) {
            createUserView(name);
        } else {
            addToInfoLabel("Twoje konto nie ma dostępu do aplikacji", Color.YELLOW);
        }
        resetLoginView();
    }

    @FXML
    void checkRepeatPassword(KeyEvent event) {
        String password = regPasswordField.getText();
        String repeatPassword = regRepeatPasswordField.getText();

        if (!repeatPassword.equals(password)) {
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

        if (!matcher.matches() && !email.equals("")) {
            addToInfoLabel("Niepoprawny format email", Color.RED);
            regEmailField.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
        } else {
            endInfoLabelFadeOut();
            regEmailField.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
        }
    }

    @FXML
    void registerUser(ActionEvent event) {
        String name = regNameField.getText().trim();
        String secondName = regSurnameField.getText().trim();
        String email = regEmailField.getText().trim();
        String login = regLoginField.getText().trim();
        String password = regPasswordField.getText().trim();
        String passwordRepeat = regRepeatPasswordField.getText().trim();

        if (login.equals("") || password.equals("") || passwordRepeat.equals("") || email.equals("")
                || name.equals("") || secondName.equals("")) {
            addToInfoLabel("Wszystkie pola muszą być wypełnione", Color.RED);
        } else {
            try {
                if (!regPasswordField.getText().equals(regRepeatPasswordField.getText())) {
                    addToInfoLabel("Hasła różią się!", Color.RED);
                } else {
                    if (userDAO.checkLogin(login)) {
                        addToInfoLabel("Istnieje już użytkownik o takim loginie! Zmień login", Color.RED);
                    } else {
                        if (userDAO.checkEmail(email)) {
                            addToInfoLabel("Istnieje już użytkownik o zadanym emailu! Zmień email", Color.RED);
                        } else {
                            if(!userDAO.registerUser(name, secondName, email, login, password).equals(Boolean.TRUE)){
                                addToInfoLabel("Coś poszło nie tak, spróbuj jeszcze raz za chwilę", Color.RED);
                            } else {
                                mailer.sendMail(email);
                                resetRegisterView();
                                addToInfoLabel("Pomyślnie zarejestrowano!", Color.GREEN);
                            }
                        }
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addToInfoLabel(String text, Color color) {
        infoLabel.setText(text);
        infoLabel.setTextFill(color);
        infoLabel.setVisible(true);
        fadeOut.playFromStart();
    }

    private void endInfoLabelFadeOut() {
        infoLabel.setVisible(false);
    }

    private void disableActiveScene(WindowEvent event) {
        activeScene = false;
    }

    private void createUserView(String name) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/userView.fxml"));
            Parent root3 = fxmlLoader.load();
            Stage userStage = new Stage();
            userStage.setTitle("Widok użytkownika " + name);
            userStage.setScene(new Scene(root3));

            UserViewController controller = fxmlLoader.getController();
            controller.setUserName(name);
            userStage.show();

            activeScene = true;
            userStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::disableActiveScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAdminView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminView.fxml"));
            Parent root2 = fxmlLoader.load();
            Stage adminStage = new Stage();
            adminStage.setTitle("Admin");
            adminStage.setScene(new Scene(root2));
            adminStage.show();

            activeScene = true;
            adminStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::disableActiveScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disableLogin() {
        loginLoginButton.setDisable(true);
        resetLoginView();
    }

    private void disableRegister() {
        regRegButton.setDisable(true);
        resetRegisterView();
    }

    private void resetLoginView() {
        loginPasswordTextField.setText("");
        loginPasswordField.setText("");
        loginLoginField.setText("");
        if (loginShowPassword.isSelected()) {
            loginShowPassword.setSelected(false);
            loginPasswordTextField.setVisible(false);
            loginPasswordField.setVisible(true);
        }
    }

    private void resetRegisterView() {
        regEmailField.setText("");
        regLoginField.setText("");
        regNameField.setText("");
        regPasswordField.setText("");
        regRepeatPasswordField.setText("");
        regSurnameField.setText("");
    }
}
