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
    private int failedLoginCounter = 0;

    @FXML
    public void initialize() {
        setupFadeOut();
        if(!checkConnection()){
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        }
        loginPasswordTextField.setVisible(false);
    }

    private void setupFadeOut() {
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
        String login = loginLoginField.getText().trim();
        String password;
        if (loginShowPassword.isSelected()) {
            password = loginPasswordTextField.getText();
        } else {
            password = loginPasswordField.getText();
        }

        if(!checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (activeScene){
            addToInfoLabel("Istnieje aktywne okno z zalogowanym użytkownikiem.", Color.DARKGOLDENROD);
        } else if (!checkIfEveryFieldIsFilled(login, password)) {
            addToInfoLabel("Pola muszą być wypełnione.", Color.RED);
        } else {
            Optional<User> user = userDAO.loginUser(login, password);
            if (!user.isPresent()) {
                addToInfoLabel("Błędne hasło lub login.", Color.RED);
                failedLoginCounter++;
            } else {
                successfulLogin(user.get().getRights(), user.get().getName());
                failedLoginCounter = 0;
            }

            if (failedLoginCounter == 3) {
                disableLogin();
                addToInfoLabel("Trzykrotnie źle wpisane hasło - blokada logowania!", Color.DARKRED);
            }
        }
    }

    private void successfulLogin(String rights, String name) {
        addToInfoLabel("Logowanie powiodło się! Witamy!", Color.GREEN);
        if (rights.equals("Admin")) {
            createAdminView();
        } else if (rights.equals("User")) {
            createUserView(name);
        } else {
            addToInfoLabel("Twoje konto nie ma dostępu do aplikacji.", Color.YELLOW);
        }
        resetLoginView();
    }

    @FXML
    void registerUser(ActionEvent event) {
        String name = regNameField.getText().trim();
        String surname = regSurnameField.getText().trim();
        String email = regEmailField.getText().trim();
        String login = regLoginField.getText().trim();
        String password = regPasswordField.getText();
        String passwordRepeat = regRepeatPasswordField.getText();
        if(!checkConnection()){
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!checkIfEveryFieldIsFilled(name, surname, email, login, password, passwordRepeat)) {
            addToInfoLabel("Wszystkie pola muszą być wypełnione.", Color.RED);
        } else if (!passwordRepeat.equals(password)) {
            addToInfoLabel("Hasła różnią się!", Color.RED);
        } else if (!checkEmailCorrectness(email)){
            addToInfoLabel("Email nie jest poprawny!", Color.RED);
        } else if (userDAO.checkLogin(login)) {
            addToInfoLabel("Istnieje już użytkownik o takim loginie - zmień login!", Color.RED);
        } else if (userDAO.checkEmail(email)) {
            addToInfoLabel("Istnieje już użytkownik o zadanym emailu - zmień email!", Color.RED);
        } else {
            if(!userDAO.registerUser(name, surname, email, login, password).equals(Boolean.TRUE)){
                addToInfoLabel("Coś poszło nie tak, spróbuj jeszcze raz za chwilę!", Color.RED);
            } else {
                mailer.sendMail(email);
                resetRegisterView();
                addToInfoLabel("Pomyślnie zarejestrowano!", Color.GREEN);
            }
        }
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
        String email = regEmailField.getText().trim();

        if (!checkEmailCorrectness(email) && !email.equals("")) {
            addToInfoLabel("Niepoprawny email", Color.RED);
            regEmailField.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
        } else {
            endInfoLabelFadeOut();
            regEmailField.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
        }
    }

    private boolean checkEmailCorrectness(String email){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean checkIfEveryFieldIsFilled(String... fields) {
        boolean isEveryFieldFilled = true;
        for (String f : fields){
            if (f.equals("")){
                isEveryFieldFilled = false;
                break;
            }
        }
        return isEveryFieldFilled;
    }

    private boolean checkConnection(){
        if (!userDAO.checkConnection()){
            disableLogin();
            disableRegister();
            return false;
        }
        return true;
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

    private void disableActiveScene(WindowEvent event) {
        activeScene = false;
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
