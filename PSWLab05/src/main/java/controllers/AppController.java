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
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppController {
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5400));
    private boolean activeScene = false;

    @FXML
    public void initialize() {
        setupFadeOut();
        loginPasswordTextField.setVisible(false);
        testConnection();
    }
    private void setupFadeOut(){
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
        if(!activeScene){
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
                                Stage adminStage = new Stage();
                                adminStage.setTitle("Admin");
                                adminStage.setScene(new Scene(root2));
                                adminStage.show();
                                activeScene = true;
                                adminStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::disableActiveScene);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }else if(rights.equals("User")){
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/userView.fxml"));
                                Parent root3 = fxmlLoader.load();
                                Stage userStage = new Stage();
                                userStage.setTitle("Widok użytkownika " + log);
                                userStage.setScene(new Scene(root3));
                                userStage.show();
                                activeScene = true;
                                userStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::disableActiveScene);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            addToInfoLabel("Twoje konto nie ma dostępu do aplikacji", Color.RED);
                            //counter++;
                        }
                        loginPasswordTextField.setText("");
                        loginPasswordField.setText("");
                        loginLoginField.setText("");
                        if(loginShowPassword.isSelected()){
                            loginShowPassword.setSelected(false);
                            loginPasswordTextField.setVisible(false);
                            loginPasswordField.setVisible(true);
                        }
                        counter = 0;
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
        } else {
            addToInfoLabel("Jest już aktywne okno", Color.RED);
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
            addToInfoLabel("Niepoprawny format email", Color.RED);
            regEmailField.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
        } else {
            endInfoLabelFadeOut();
            regEmailField.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
        }
    }
    @FXML
    void registerUser(ActionEvent event) {
        if(regLoginField.getText().equals("")||regPasswordField.getText().equals("")||regRepeatPasswordField.getText().equals("")||regEmailField.getText().equals("")
        ||regNameField.getText().equals("")||regSurnameField.getText().equals("")){
            addToInfoLabel("Pola: Name, Surname, Email, Login, Password, Repeat Password are required!!!", Color.RED);
        }else{
            try{
                if(regPasswordField.getText().equals(regRepeatPasswordField.getText())){
                    if(loginExists()){
                        addToInfoLabel("Istnieje już użytkownik o takim loginie! Zmień login", Color.RED);
                    }else {
                        if(emailExists()){
                            addToInfoLabel("Istnieje już użytkownik o zadanym emailu! Zmień email", Color.RED);
                        }else{
                            dbconn();
                            String name = regNameField.getText();
                            String secondName = regSurnameField.getText();
                            String email = regEmailField.getText();
                            String login = regLoginField.getText();
                            String password = regPasswordField.getText();
                            statement = connector.prepareStatement("INSERT INTO userdatabase " +
                                    "VALUES (NULL,?,?,?,?,?,'User',NOW())");
                            ((PreparedStatement) statement).setString(1, name);
                            ((PreparedStatement) statement).setString(2, secondName);
                            ((PreparedStatement) statement).setString(3, login);
                            ((PreparedStatement) statement).setString(4, password);
                            ((PreparedStatement) statement).setString(5, email);
                            ((PreparedStatement) statement).execute();
                            //sendMail();
                        }
                    }
                }else{
                    addToInfoLabel("Hasła różią się!", Color.RED);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void testConnection(){
        dbconn();
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
    boolean loginExists(){
        String login=regLoginField.getText();
        String loginQuery;
        boolean prawda=false;
        dbconn();
        try {
            resultSet = statement.executeQuery("select login from userdatabase");
            while (resultSet.next()) {
                loginQuery=resultSet.getString(1);
                if(loginQuery.equals(login)){
                    prawda=true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(prawda){
            return true;
        }else{
            return false;
        }
    }
    boolean emailExists(){
        String email=regEmailField.getText();
        String emailQuery;
        boolean prawda=false;
        dbconn();
        try {
            resultSet = statement.executeQuery("select email from userdatabase");
            while (resultSet.next()) {
                emailQuery=resultSet.getString("email");
                if(emailQuery.equals(email)){
                    prawda=true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(prawda){
            return true;
        }else{
            return false;
        }
    }

    private Connection connector;
    private Statement statement;
    private ResultSet resultSet;
    public void dbconn(){
        try{
            connector = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginapp?useLegacyDatetimeCode=false&serverTimezone=UTC","root","zaq1@WSX");
            statement = connector.createStatement();
        }catch (Exception ex){
            addToInfoLabel("Brak połączenia z bazą!", Color.RED);
            ex.printStackTrace();
        }
    }
    void sendMail(){
        final String username = "javawindowslab";
        final String password = "j@vawindows123";
        String fromEmail = "javawindowslab@gmail.com";
        String toEmail = regEmailField.getText();
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","mail.smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
            });
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
            msg.setSubject("Rejestracja app laby");
            msg.setText("Witaj w aplikacji");

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com",username,password);
            transport.sendMessage(msg,msg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private void disableActiveScene(WindowEvent event){
        activeScene = false;
    }
}
