package controllers;

import impls.EventDAOImpl;
import impls.UserDAOImpl;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import models.Event;
import models.User;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminViewController {
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5400));
    private UserDAOImpl userDAO = new UserDAOImpl();
    private EventDAOImpl eventDAO = new EventDAOImpl();
    private ArrayList<User> users;
    private ArrayList<Event> events;
    private String userLogin = "";

    public void initialize() {
        setupTableClickHandlers();
        setupTableViews();
        setupFadeOut();
        getUsers();
        getEvents();
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    private void setupFadeOut() {
        fadeOut.setNode(infoLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
    }

    private void setupTableClickHandlers() {
        userTableView.setRowFactory(tv -> {
            TableRow<User> rowUser = new TableRow<>();
            rowUser.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !rowUser.isEmpty()) {
                    User user = rowUser.getItem();
                    userIdTextField.setText(user.getId().toString());
                    userLoginTextField.setText(user.getLogin());
                    userPasswordTextField.setText(user.getPassword());
                    userNameTextField.setText(user.getName());
                    userSurnameTextField.setText(user.getSurname());
                    userEmailTextField.setText(user.getEmail());
                    userRightsTextField.setText(user.getRights());
                    userDateTextField.setText(user.getDate().toString());
                }
            });
            return rowUser;
        });

        eventTableView.setRowFactory(tv -> {
            TableRow<Event> rowEvent = new TableRow<>();
            rowEvent.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !rowEvent.isEmpty()) {
                    Event eventObject = rowEvent.getItem();
                    eventIdTextField.setText(eventObject.getId().toString());
                    eventNameTextField.setText(eventObject.getName());
                    eventAgendaTextField.setText(eventObject.getAgenda());
                    eventDateTextField.setText(eventObject.getDate().toString());
                }
            });
            return rowEvent;
        });
    }

    private void setupTableViews() {
        col_user_id.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));
        col_user_login.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        col_user_password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        col_user_name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        col_user_surname.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        col_user_email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        col_user_rights.setCellValueFactory(new PropertyValueFactory<User, String>("rights"));
        col_user_date.setCellValueFactory(new PropertyValueFactory<User, Date>("date"));

        col_event_id.setCellValueFactory(new PropertyValueFactory<Event, Long>("id"));
        col_event_name.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
        col_event_agenda.setCellValueFactory(new PropertyValueFactory<Event, String>("agenda"));
        col_event_date.setCellValueFactory(new PropertyValueFactory<Event, Date>("date"));
    }

    @FXML
    private Label infoLabel;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, Long> col_user_id;

    @FXML
    private TableColumn<User, String> col_user_login;

    @FXML
    private TableColumn<User, String> col_user_password;

    @FXML
    private TableColumn<User, String> col_user_name;

    @FXML
    private TableColumn<User, String> col_user_surname;

    @FXML
    private TableColumn<User, String> col_user_email;

    @FXML
    private TableColumn<User, String> col_user_rights;

    @FXML
    private TableColumn<User, Date> col_user_date;

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField userLoginTextField;

    @FXML
    private TextField userPasswordTextField;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField userSurnameTextField;

    @FXML
    private TextField userEmailTextField;

    @FXML
    private TextField userRightsTextField;

    @FXML
    private TextField userDateTextField;

    @FXML
    private TableView<Event> eventTableView;

    @FXML
    private TableColumn<Event, Long> col_event_id;

    @FXML
    private TableColumn<Event, String> col_event_name;

    @FXML
    private TableColumn<Event, String> col_event_agenda;

    @FXML
    private TableColumn<Event, Date> col_event_date;

    @FXML
    private TextField eventIdTextField;

    @FXML
    private TextField eventNameTextField;

    @FXML
    private TextField eventAgendaTextField;

    @FXML
    private TextField eventDateTextField;

    @FXML
    private ComboBox<User> signUsersComboBox;

    @FXML
    private ComboBox<Event> signEventComboBox;

    @FXML
    void checkDateEvent(KeyEvent event) {
        String date = eventDateTextField.getText().trim();

        if (!checkDateCorectness(date) && !date.equals("")) {
            addToInfoLabel("Niepoprawna data", Color.RED);
            eventDateTextField.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
        } else {
            endInfoLabelFadeOut();
            eventDateTextField.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
        }
    }

    @FXML
    void checkDateUser(KeyEvent event) {
        String date = userDateTextField.getText().trim();

        if (!checkDateCorectness(date) && !date.equals("")) {
            addToInfoLabel("Niepoprawna data", Color.RED);
            userDateTextField.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
        } else {
            endInfoLabelFadeOut();
            userDateTextField.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
        }
    }

    @FXML
    void checkEmail(KeyEvent event) {
        String email = userEmailTextField.getText().trim();

        if (!checkEmailCorrectness(email) && !email.equals("")) {
            addToInfoLabel("Niepoprawna data", Color.RED);
            userEmailTextField.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
        } else {
            endInfoLabelFadeOut();
            userEmailTextField.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
        }

    }

    @FXML
    void addEvent(ActionEvent event) {
        //Long id = Long.parseLong(eventIdTextField.getText());
        String name = eventNameTextField.getText();
        String agenda = eventAgendaTextField.getText();
        String dateTemp = eventDateTextField.getText().trim().toLowerCase();
        String date = dateTemp.equals("now") ? nowDate() : dateTemp;

        if (!eventDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!checkIfEveryFieldIsFilled(name, agenda, date)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!checkDateCorectness(date)) {
            addToInfoLabel("Data jest w złym formacie", Color.RED);
        } else {
            Event eventObject = new Event(name, agenda, Date.valueOf(date));
            if (eventDAO.addEvent(eventObject)) {
                events.add(eventDAO.getNewestEvent());
                eventTableView.refresh();
                addToInfoLabel("Pomyślnie dodano użytkownika", Color.GREEN);
            }
        }
    }

    @FXML
    void addUser(ActionEvent event) {
        //Long id = Long.parseLong(userIdTextField.getText());
        String login = userLoginTextField.getText().trim();
        String password = userPasswordTextField.getText().trim();
        String name = userNameTextField.getText().trim();
        String surname = userSurnameTextField.getText().trim();
        String email = userEmailTextField.getText().trim();
        String rights = userRightsTextField.getText().trim();
        String dateTemp = userDateTextField.getText().trim().toLowerCase();
        String date = dateTemp.equals("now") ? nowDate() : dateTemp;

        if (!userDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!checkIfEveryFieldIsFilled(login, password, name, surname, email, rights, date)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!checkDateCorectness(date)) {
            addToInfoLabel("Data jest w złym formacie", Color.RED);
        } else if (!checkEmailCorrectness(email)) {
            addToInfoLabel("Email jest w złym formacie", Color.RED);
        } else {
            User user = new User(name, surname, email, login, password, rights, Date.valueOf(date));
            if (userDAO.addUser(user)) {
                users.add(userDAO.getNewestUser());
                userTableView.refresh();
                addToInfoLabel("Pomyślnie dodano użytkownika", Color.GREEN);
            } else {
                addToInfoLabel("Dodawanie nie powiodło się - spróbuj później!", Color.RED);
            }
        }


    }

    @FXML
    void deleteEvent(ActionEvent event) {
        String idString = eventIdTextField.getText().trim();
        Long id = !idString.equals("") ? Long.parseLong(idString) : 0;
        if (!eventDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (id.equals(0)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!eventDAO.checkEventId(id)) {
            addToInfoLabel("Brak wydarzenia o podanym id", Color.RED);
        } else {
            if (eventDAO.deleteEvent(id)) {
                deleteEventFromTableView(id);
                addToInfoLabel("Pomyślnie usunięto wydarzenie", Color.GREEN);
            } else {
                addToInfoLabel("Usuwanie nie powiodło się - spróbuj później!", Color.RED);
            }
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {
        String idString = userIdTextField.getText().trim();
        Long id = !idString.equals("") ? Long.parseLong(idString) : 0;
        String login = getLoginFromTableView(id);
        if (!userDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (id.equals(0)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!userDAO.checkUserId(id)) {
            addToInfoLabel("Brak użytkownika o podanym id", Color.RED);
        } else if (login.equals(userLogin)) {
            addToInfoLabel("Nie możesz usunąć siebie", Color.RED);
        } else {
            if (userDAO.deleteUser(id)) {
                deleteUserFromTableView(id);
                addToInfoLabel("Pomyślnie usunięto użytkownika", Color.GREEN);
            } else {
                addToInfoLabel("Usuwanie nie powiodło się - spróbuj później!", Color.RED);
            }
        }
    }

    @FXML
    void modifyEvent(ActionEvent event) {

    }

    @FXML
    void resetUserPassword(ActionEvent event) {
        String idString = userIdTextField.getText().trim();
        Long id = !idString.equals("") ? Long.parseLong(idString) : 0;
        String password = userPasswordTextField.getText();
        if (!userDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!checkIfEveryFieldIsFilled(idString, password)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!userDAO.checkUserId(id)) {
            addToInfoLabel("Brak użytkownika o podanym id", Color.RED);
        } else {
            if (userDAO.resetPassword(id, password)) {
                updatePasswordInTableView(id, password);
                addToInfoLabel("Pomyślnie zmieniono hasło", Color.GREEN);
            } else {
                addToInfoLabel("Zmiana nie powiodła się - spróbuj później", Color.RED);
            }
        }
    }

    @FXML
    void signAccept(ActionEvent event) {

    }

    @FXML
    void signAcceptAll(ActionEvent event) {

    }

    @FXML
    void signDeny(ActionEvent event) {

    }

    private void getUsers() {
        users = userDAO.getUsers();
        ObservableList<User> usersObservable = FXCollections.observableList(users);
        userTableView.setItems(usersObservable);
    }

    private void getEvents() {
        events = eventDAO.getEvents();
        ObservableList<Event> eventsObservable = FXCollections.observableList(events);
        eventTableView.setItems(eventsObservable);
    }

    private void updatePasswordInTableView(Long id, String password) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                u.setPassword(password);
                userTableView.refresh();
                break;
            }
        }
    }

    private void deleteEventFromTableView(Long id) {
        for (Event e : events) {
            if (e.getId().equals(id)) {
                events.remove(e);
                eventTableView.refresh();
                break;
            }
        }
    }

    private void deleteUserFromTableView(Long id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                users.remove(u);
                userTableView.refresh();
                break;
            }
        }
    }

    private String getLoginFromTableView(Long id) {
        String login = "";
        for (User u : users) {
            if (u.getId().equals(id)) {
                login = u.getLogin();
                break;
            }
        }
        return login;
    }

    private boolean checkEmailCorrectness(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean checkDateCorectness(String date) {
        String regex = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    private String nowDate() {
        String date;
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = formatter.format(ldt);
        return date;
    }

    private boolean checkIfEveryFieldIsFilled(String... fields) {
        boolean isEveryFieldFilled = true;
        for (String f : fields) {
            if (f.equals("")) {
                isEveryFieldFilled = false;
                break;
            }
        }
        return isEveryFieldFilled;
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

}
