package controllers;

import impls.EventDAOImpl;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import models.Event;

import java.util.ArrayList;

public class UserViewController {
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5400));
    private EventDAOImpl eventDAO = new EventDAOImpl();

    public void initialize(){
        setupFadeOut();
        addEvents();
        addFoodTypes();
        addParticipantTypes();
    }


    @FXML
    private Label topLabel;
    public void setUserName(String userName){
        topLabel.setText("Witaj " + userName + "!");
    }
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

    }

    @FXML
    void selectEvent(ActionEvent event) {
        Event selectedEvent = nameComboBox.getSelectionModel().getSelectedItem();
        agendaField.setText(selectedEvent.getAgenda());
        dateField.setText(selectedEvent.getDate().toString());
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
    private void endInfoLabelFadeOut(){
        infoLabel.setVisible(false);
    }
}
