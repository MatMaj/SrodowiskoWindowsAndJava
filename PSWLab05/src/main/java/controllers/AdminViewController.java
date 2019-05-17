package controllers;

import impls.EventDAOImpl;
import impls.UserDAOImpl;
import impls.UserEventDAOImpl;
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
import models.UserEvent;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminViewController {
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5400));
    private UserDAOImpl userDAO = new UserDAOImpl();
    private EventDAOImpl eventDAO = new EventDAOImpl();
    private UserEventDAOImpl userEventDAO = new UserEventDAOImpl();
    private ArrayList<User> users;
    private List<Event> events;
    private List<UserEvent> userEvents;
    private String userLogin = "";
    private String oldPassword = "";
    private Event oldEvent;

    public void initialize() {
        setupTableClickHandlers();
        setupTableViews();
        setupFadeOut();
        getUsers();
        getEvents();
        getUserEvents();
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
                    oldPassword = user.getPassword();
                    userIdTextField.setText(user.getId().toString());
                    userLoginTextField.setText(user.getLogin());
                    userPasswordTextField.setText(oldPassword);
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
                    oldEvent = eventObject;
                    eventIdTextField.setText(eventObject.getId().toString());
                    eventNameTextField.setText(eventObject.getName());
                    eventAgendaTextField.setText(eventObject.getAgenda());
                    eventDateTextField.setText(eventObject.getDate().toString());
                }
            });
            return rowEvent;
        });

        userEventTableView.setRowFactory(tv -> {
            TableRow<UserEvent> rowUserEvent = new TableRow<>();
            rowUserEvent.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !rowUserEvent.isEmpty()) {
                    UserEvent userEvent = rowUserEvent.getItem();
                    userEventUserIdTextField.setText(userEvent.getUser_id().toString());
                    userEventEventIdTextField.setText(userEvent.getEvent_id().toString());
                    userEventAcceptedTextField.setText(String.valueOf(userEvent.getAccepted()));
                    userEventParticipantTextField.setText(userEvent.getParticipant());
                    userEventFoodTextField.setText(userEvent.getFood());
                }
            });
            return rowUserEvent;
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

        col_userEvent_userId.setCellValueFactory(new PropertyValueFactory<UserEvent, Long>("user_id"));
        col_userEvent_eventId.setCellValueFactory(new PropertyValueFactory<UserEvent, Long>("event_id"));
        col_userEvent_accepted.setCellValueFactory(new PropertyValueFactory<UserEvent, Short>("accepted"));
        col_userEvent_participant.setCellValueFactory(new PropertyValueFactory<UserEvent, String>("participant"));
        col_userEvent_food.setCellValueFactory(new PropertyValueFactory<UserEvent, String>("food"));
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
    private TableView<UserEvent> userEventTableView;
    @FXML
    private TableColumn<UserEvent, Long> col_userEvent_userId;
    @FXML
    private TableColumn<UserEvent, Long> col_userEvent_eventId;
    @FXML
    private TableColumn<UserEvent, Short> col_userEvent_accepted;
    @FXML
    private TableColumn<UserEvent, String> col_userEvent_participant;
    @FXML
    private TableColumn<UserEvent, String> col_userEvent_food;
    @FXML
    private TextField userEventUserIdTextField;
    @FXML
    private TextField userEventEventIdTextField;
    @FXML
    private TextField userEventAcceptedTextField;
    @FXML
    private TextField userEventParticipantTextField;
    @FXML
    private TextField userEventFoodTextField;

    @FXML
    void checkDateEvent(KeyEvent event) {
        String date = eventDateTextField.getText().trim().toLowerCase();

        if (!checkDateCorrectness(date) && !date.equals("") && !date.equals("now")) {
            addToInfoLabel("Niepoprawna data", Color.RED);
            eventDateTextField.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
        } else {
            endInfoLabelFadeOut();
            eventDateTextField.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
        }
    }

    @FXML
    void checkDateUser(KeyEvent event) {
        String date = userDateTextField.getText().trim().toLowerCase();

        if (!checkDateCorrectness(date) && !date.equals("") && !date.equals("now")) {
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
    void checkAccepted(KeyEvent event) {
        String accepted = userEventAcceptedTextField.getText().trim();
        if (checkAcceptedCorrectness(accepted) || accepted.equals("")) {
            endInfoLabelFadeOut();
            userEventAcceptedTextField.setStyle("-fx-background-color: white; -fx-border-color: #B5B5B5; -fx-border-radius: 3px;");
        } else {
            addToInfoLabel("Nieprawidłowa wartość accepted", Color.RED);
            userEventAcceptedTextField.setStyle("-fx-background-color: rgba(255,0,0,0.5); -fx-border-color: rgba(255,0,0,0.75); -fx-border-radius: 3px;");
        }
    }

    @FXML
    void addEvent(ActionEvent event) {
        String name = eventNameTextField.getText();
        String agenda = eventAgendaTextField.getText();
        String dateTemp = eventDateTextField.getText().trim().toLowerCase();
        String date = dateTemp.equals("now") ? nowDate() : dateTemp;

        if (!eventDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!checkIfEveryFieldIsFilled(name, agenda, date)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!checkDateCorrectness(date)) {
            addToInfoLabel("Data jest w złym formacie", Color.RED);
        } else {
            Event eventObject = new Event(0L,name, agenda, Date.valueOf(date));
            eventDAO.addEvent(eventObject);
            events.add(eventDAO.getNewestEvent());
            eventTableView.refresh();
            clearEventFields();

        }
    }

    @FXML
    void addUser(ActionEvent event) {
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
        } else if (!checkDateCorrectness(date)) {
            addToInfoLabel("Data jest w złym formacie", Color.RED);
        } else if (!checkEmailCorrectness(email)) {
            addToInfoLabel("Email jest w złym formacie", Color.RED);
        } else {
            User user = new User(name, surname, email, login, password, rights, Date.valueOf(date));
            if (userDAO.addUser(user)) {
                users.add(userDAO.getNewestUser());
                userTableView.refresh();
                clearUserFields();
                addToInfoLabel("Pomyślnie dodano użytkownika", Color.GREEN);
            } else {
                addToInfoLabel("Dodawanie nie powiodło się - spróbuj później!", Color.RED);
            }
        }


    }

    @FXML
    void deleteEvent(ActionEvent event) {
        String idString = eventIdTextField.getText().trim();
        if (!eventDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!isNumeric(idString)) {
            addToInfoLabel("Id musi być liczbą całkowitą", Color.RED);
        } else if (eventDAO.checkEventId(Long.parseLong(idString)).isEmpty()){
            addToInfoLabel("Brak wydarzenia o podanym id", Color.RED);
        } else {
            Long id = Long.parseLong(idString);
            eventDAO.deleteEvent(id);
            userEventDAO.deleteAllEvent(id);
            deleteEventFromTableView(id);
            clearEventFields();
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {
        String idString = userIdTextField.getText().trim();
        String login = "";
        if(isNumeric(idString))
            login = getLoginFromTableView(Long.parseLong(idString));
        if (!userDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!isNumeric(idString)) {
            addToInfoLabel("Id musi być liczbą całkowitą", Color.RED);
        } else if (login.equals(userLogin)) {
            addToInfoLabel("Nie możesz usunąć siebie", Color.RED);
        } else if (!userDAO.checkUserId(Long.parseLong(idString))) {
            addToInfoLabel("Brak użytkownika o podanym id", Color.RED);
        } else {
            Long id = Long.parseLong(idString);
            if (userDAO.deleteUser(id)) {
                userEventDAO.deleteAllUser(id);
                deleteUserFromTableView(id);
                clearUserFields();
                addToInfoLabel("Pomyślnie usunięto użytkownika", Color.GREEN);
            } else {
                addToInfoLabel("Usuwanie nie powiodło się - spróbuj później!", Color.RED);
            }
        }
    }

    @FXML
    void deleteUserevent(ActionEvent event) {
        String userIdString = userEventUserIdTextField.getText();
        String eventIdString = userEventEventIdTextField.getText();
        String acceptedString = userEventAcceptedTextField.getText();
        String participantType = userEventParticipantTextField.getText();
        String foodType = userEventFoodTextField.getText();
        if (!userEventDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!isNumeric(userIdString) || !isNumeric(eventIdString)) {
            addToInfoLabel("Id muszą być liczbą całkowitą", Color.RED);
        } else if (!checkIfEveryFieldIsFilled(userIdString, eventIdString, acceptedString)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!checkAcceptedCorrectness(acceptedString)) {
            addToInfoLabel("Nieprawidłowa wartość accepted", Color.RED);
        } else {
            Long userId = Long.parseLong(userIdString);
            Long eventId = Long.parseLong(eventIdString);
            short accepted = Short.valueOf(acceptedString);
            UserEvent userEvent = new UserEvent(userId, eventId, accepted, participantType, foodType);
            if (userEventDAO.deleteUserFromEvent(userEvent)) {
                deleteUserEventFromTableView(userEvent);
                addToInfoLabel("Usunięto pomyślnie", Color.GREEN);
            } else {
                addToInfoLabel("Coś poszło nie tak - spóbuj później!", Color.RED);
            }
        }
    }

    @FXML
    void modifyEvent(ActionEvent event) {
        String idString = eventIdTextField.getText();
        String name = eventNameTextField.getText();
        String agenda = eventAgendaTextField.getText();
        String dateTemp = eventDateTextField.getText().trim().toLowerCase();
        String dateString = dateTemp.equals("now") ? nowDate() : dateTemp;
        //System.out.println(agenda);
        if(!eventDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!checkIfEveryFieldIsFilled(idString, name, agenda, dateString)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!isNumeric(idString)) {
            addToInfoLabel("Id musi być liczbą całkowitą", Color.RED);
        } else if (!checkDateCorrectness(dateString)) {
            addToInfoLabel("Data jest w złym formacie", Color.RED);
        } else if (eventDAO.checkEventId(Long.parseLong(idString)).isEmpty()) {
            addToInfoLabel("Brak wydarzenia o podanym id", Color.RED);
        } else {
            Long id = Long.parseLong(idString);
            Date date = Date.valueOf(dateString);
            Event eventObject = new Event(id, name, agenda, date);
            if (oldEvent.getDate().equals(date) && oldEvent.getName().equals(name) && oldEvent.getAgenda().equals(agenda)) {
                addToInfoLabel("Nic się nie zmieni", Color.DARKGOLDENROD);
            } else {
                eventDAO.modifyEvent(eventObject);
                modifyEventInTableView(eventObject);
                clearEventFields();
            }
        }
    }

    @FXML
    void resetUserPassword(ActionEvent event) {
        String idString = userIdTextField.getText().trim();
        Long id = !idString.equals("") ? Long.parseLong(idString) : 0;
        String password = userPasswordTextField.getText().trim();
        if (!userDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (password.equals(oldPassword)) {
            addToInfoLabel("Hasło jest takie same", Color.RED);
        } else if (!isNumeric(idString)){
            addToInfoLabel("Id musi być liczbą całkowitą", Color.RED);
        } else if (!checkIfEveryFieldIsFilled(idString, password)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!userDAO.checkUserId(id)) {
            addToInfoLabel("Brak użytkownika o podanym id", Color.RED);
        } else {
            if (userDAO.resetPassword(id, password)) {
                updatePasswordInTableView(id, password);
                clearUserFields();
                addToInfoLabel("Pomyślnie zmieniono hasło", Color.GREEN);
            } else {
                addToInfoLabel("Zmiana nie powiodła się - spróbuj później", Color.RED);
            }
        }
    }

    @FXML
    void clearEvent(ActionEvent event) {
        clearEventFields();
    }

    @FXML
    void clearUser(ActionEvent event) {
        clearUserFields();
    }

    @FXML
    void signAccept(ActionEvent event) {
        String userIdString = userEventUserIdTextField.getText();
        String eventIdString = userEventEventIdTextField.getText();
        if (!userEventDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!checkIfEveryFieldIsFilled(userIdString, eventIdString)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!isNumeric(userIdString) || !isNumeric(eventIdString)){
            addToInfoLabel("Id muszą być liczbą całkowitą", Color.RED);
        } else if (!userDAO.checkUserId(Long.parseLong(userIdString))) {
            addToInfoLabel("Brak użytkownika o podanym id", Color.RED);
        } else if (eventDAO.checkEventId(Long.parseLong(eventIdString)).isEmpty()) {
            addToInfoLabel("Brak wydarzenia o podanym id", Color.RED);
        } else {
            Long userId = Long.parseLong(userIdString);
            Long eventId = Long.parseLong(eventIdString);
            userEventDAO.acceptUserInEvent(new UserEvent(userId, eventId, (short) 1));
            acceptUserInEventInTableView(userId, eventId, (short) 1);
            clearUserEventFields();
        }
    }

    @FXML
    void signAcceptAll(ActionEvent event) {
        if (!userEventDAO.checkConnection()){
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else {
            userEventDAO.acceptEveryUser();
            acceptEveryUserInTableView();
        }
    }

    @FXML
    void signDeny(ActionEvent event) {
        String userIdString = userEventUserIdTextField.getText();
        String eventIdString = userEventEventIdTextField.getText();
        if (!userEventDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (!checkIfEveryFieldIsFilled(userIdString, eventIdString)) {
            addToInfoLabel("Któreś z wymaganych pól jest puste", Color.RED);
        } else if (!isNumeric(userIdString) || !isNumeric(eventIdString)) {
            addToInfoLabel("Id muszą być liczbą całkowitą", Color.RED);
        } else if (!userDAO.checkUserId(Long.parseLong(userIdString))) {
            addToInfoLabel("Brak użytkownika o podanym id", Color.RED);
        } else if (eventDAO.checkEventId(Long.parseLong(eventIdString)).isEmpty()) {
            addToInfoLabel("Brak wydarzenia o podanym id", Color.RED);
        } else {
            Long userId = Long.parseLong(userIdString);
            Long eventId = Long.parseLong(eventIdString);
            userEventDAO.acceptUserInEvent(new UserEvent(userId, eventId, (short) 2));
            acceptUserInEventInTableView(userId, eventId, (short) 2);
            clearUserEventFields();
        }
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

    private void getUserEvents() {
        userEvents = userEventDAO.getAllUserEvent();
        ObservableList<UserEvent> userEventsObservable = FXCollections.observableList(userEvents);
        userEventTableView.setItems(userEventsObservable);
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

    private void modifyEventInTableView(Event event) {
        for (Event e : events) {
            if (e.getId().equals(event.getId())) {
                e.setName(event.getName());
                e.setAgenda(event.getAgenda());
                e.setDate(event.getDate());
                eventTableView.refresh();
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
        deleteUserEVENTFromTableView(id);
    }

    private void deleteUserFromTableView(Long id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                users.remove(u);
                userTableView.refresh();
                break;
            }
        }
        deleteUSEREventFromTableView(id);
    }

    private void deleteUserEventFromTableView(UserEvent userEvent) {
        for (UserEvent ue : userEvents) {
            if (ue.getUser_id().equals(userEvent.getUser_id()) && ue.getEvent_id().equals(userEvent.getEvent_id())) {
                userEvents.remove(ue);
                userEventTableView.refresh();
                break;
            }
        }

    }

    private void deleteUserEVENTFromTableView(Long eventId) {
        for (UserEvent ue : userEvents) {
            if (ue.getEvent_id().equals(eventId)) {
                userEvents.remove(ue);
            }
        }
        userEventTableView.refresh();
    }

    private void deleteUSEREventFromTableView(Long userId) {
        for (UserEvent ue : userEvents) {
            if (ue.getUser_id().equals(userId)) {
                userEvents.remove(ue);
            }
        }
        userEventTableView.refresh();
    }

    private void acceptEveryUserInTableView() {
        for (UserEvent ue : userEvents) {
            if (ue.getAccepted() == 0) {
                ue.setAccepted((short) 1);
            }
        }
        userEventTableView.refresh();
    }

    private void acceptUserInEventInTableView(Long userId, Long eventId, short accpeted) {
        for (UserEvent ue : userEvents) {
            if (ue.getUser_id().equals(userId) && ue.getEvent_id().equals(eventId)) {
                ue.setAccepted(accpeted);
                userEventTableView.refresh();
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

    private boolean checkDateCorrectness(String date) {
        String regex = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    private boolean checkAcceptedCorrectness(String accepted){
        return accepted.equals("0") || accepted.equals("1") || accepted.equals("2");
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
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

    private void clearUserFields(){
        userIdTextField.setText("");
        userLoginTextField.setText("");
        userPasswordTextField.setText("");
        userNameTextField.setText("");
        userSurnameTextField.setText("");
        userEmailTextField.setText("");
        userRightsTextField.setText("");
        userDateTextField.setText("");
    }

    private void clearEventFields() {
        eventIdTextField.setText("");
        eventNameTextField.setText("");
        eventAgendaTextField.setText("");
        eventDateTextField.setText("");
    }

    private void clearUserEventFields() {
        userEventUserIdTextField.setText("");
        userEventEventIdTextField.setText("");
        userEventAcceptedTextField.setText("");
        userEventFoodTextField.setText("");
        userEventParticipantTextField.setText("");
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