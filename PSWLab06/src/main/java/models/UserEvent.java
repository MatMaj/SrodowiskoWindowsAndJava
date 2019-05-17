package models;

import javax.persistence.*;

@Entity
@Table(name="user_event")
public class UserEvent {
    @Id
    private Long id;
    private Long user_id;
    private Long event_id;
    private Short accepted;
    private String food;
    private String participant;

    public UserEvent(Long user_id, Long event_id, Short accepted, String participant, String food) {
        this.user_id = user_id;
        this.event_id = event_id;
        this.accepted = accepted;
        this.food = food;
        this.participant = participant;
    }

    public UserEvent(Long user_id, Long event_id, Short accepted) {
        this.user_id = user_id;
        this.event_id = event_id;
        this.accepted = accepted;
    }

    public UserEvent(){

    }

    public Long getUser_id() {
        return user_id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public Short getAccepted() {
        return accepted;
    }

    public String getFood() {
        return food;
    }

    public String getParticipant() {
        return participant;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public void setAccepted(Short accepted) {
        this.accepted = accepted;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}
