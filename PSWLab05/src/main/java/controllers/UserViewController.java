package controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

public class UserViewController {
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5400));
    private String userName = "test1";

    public void initialize(){
        setupFadeOut();
        setupEvents();
        addFoodTypes();
        addParticipantType();
        //addToInfoLabel("Elocha", Color.rgb(10,20,30));
    }

    public void setUserName(String userName){
        topLabel.setText("Witaj " + userName + "!");
    }
    @FXML
    private Label topLabel;

    @FXML
    private ComboBox<String> nameEvent;

    @FXML
    private TextField agendaEvent;

    @FXML
    private TextField dateEvent;

    @FXML
    private ComboBox<String> typeEvent;

    @FXML
    private ComboBox<String> eatEvent;

    @FXML
    private Label infoLabel;

    @FXML
    void addToEvent(ActionEvent event) {

    }

    private void setupFadeOut(){
        fadeOut.setNode(infoLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
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

    private void setupEvents(){
        ArrayList<String> events = new ArrayList<String>();
        /*
        jeżeli są jakieś eventy w bazie
        dodaj do comboboxa
        jeżeli nie to format c
        */
        events.add("Siema1");
        events.add("Siema2");
        events.add("Siema3");

        addEvents(events);
    }

    private void addEvents(ArrayList<String> events){
        ObservableList<String> eventsCombobox = FXCollections.observableArrayList();
        eventsCombobox.addAll(events);
        nameEvent.setItems(eventsCombobox);
    }

    private void addFoodTypes(){
        ObservableList<String> foodTypes = FXCollections.observableArrayList();
        foodTypes.addAll("Bez preferencji", "Wegetariańskie", "Bez glutenu");
        eatEvent.setItems(foodTypes);
    }

    private void addParticipantType(){
        ObservableList<String> participantTypes = FXCollections.observableArrayList();
        participantTypes.addAll("Słuchacz", "Autor", "Sponsor", "Organizator");
        typeEvent.setItems(participantTypes);
    }

}
