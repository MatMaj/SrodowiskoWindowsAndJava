package controllers;

import impls.EventDAOImpl;
import impls.UserEventDAOImpl;
import interfaces.UserEventDAO;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import models.Event;
import models.UserEvent;

import java.util.ArrayList;

public class UserViewController {
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5400));
    private EventDAOImpl eventDAO = new EventDAOImpl();
    private UserEventDAOImpl userEventDAO = new UserEventDAOImpl();
    private Long userId;
    private Long eventId;

    public void initialize(){
        setupFadeOut();
        addEvents();
        addFoodTypes();
        addParticipantTypes();
    }


    @FXML
    private Label topLabel;
    @FXML
    private ComboBox<Event> nameComboBox;
    @FXML
    private TextField agendaField;
    @FXML
    private TextField dateField;
    @FXML
    private ComboBox<String> participantTypeComboBox;
    @FXML
    private ComboBox<String> foodTypeComboBox;
    @FXML
    private Label infoLabel;

    @FXML
    void joinEvent(ActionEvent event) {
        String participantType = participantTypeComboBox.getSelectionModel().getSelectedItem();
        String foodType = foodTypeComboBox.getSelectionModel().getSelectedItem();
        UserEvent userEvent = new UserEvent(userId, eventId, (short) 0, participantType, foodType);
        if (!userEventDAO.checkConnection()) {
            addToInfoLabel("Brak połączenia z bazą - sprawdź połączenie!", Color.DARKRED);
        } else if (userEventDAO.checkUserInEvent(userEvent)) {
            addToInfoLabel("Jesteś już zapisany na to wydarzenie", Color.DARKGOLDENROD);
        } else {
            if (userEventDAO.addUserToEvent(userEvent)) {
                addToInfoLabel("Pomyślnie zapisano", Color.GREEN);
            } else {
                addToInfoLabel("Coś poszło nie tak - spróbuj później", Color.RED);
            }
        }
    }

    @FXML
    void selectEvent(ActionEvent event) {
        Event selectedEvent = nameComboBox.getSelectionModel().getSelectedItem();
        agendaField.setText(selectedEvent.getAgenda());
        dateField.setText(selectedEvent.getDate().toString());
        eventId = selectedEvent.getId();
    }

    private void setupFadeOut(){
        fadeOut.setNode(infoLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
    }

    private void addEvents(){
        ArrayList<Event> events = eventDAO.getEvents();
        ObservableList<Event> eventsObservableList = FXCollections.observableList(events);
        nameComboBox.setItems(eventsObservableList);
        //nameComboBox.getSelectionModel().selectFirst();
    }

    private void addFoodTypes(){
        ObservableList<String> foodTypes = FXCollections.observableArrayList();
        foodTypes.addAll("Bez preferencji", "Wegetariańskie", "Bez glutenu");
        foodTypeComboBox.setItems(foodTypes);
        foodTypeComboBox.getSelectionModel().selectFirst();
    }

    private void addParticipantTypes(){
        ObservableList<String> participantTypes = FXCollections.observableArrayList();
        participantTypes.addAll("Słuchacz", "Autor", "Sponsor", "Organizator");
        participantTypeComboBox.setItems(participantTypes);
        participantTypeComboBox.getSelectionModel().selectFirst();
    }

    private void addToInfoLabel(String text, Color color){
        infoLabel.setText(text);
        infoLabel.setTextFill(color);
        infoLabel.setVisible(true);
        fadeOut.playFromStart();
    }

    public void setUserName(String userName){
        topLabel.setText("Witaj " + userName + "!");
    }

    public void setUserId(Long id) {
        this.userId = id;
    }
}
