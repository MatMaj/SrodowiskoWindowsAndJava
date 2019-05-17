package models;

public class UserEvent {
    private Long userId;
    private Long eventId;
    private Short accepted;
    private String foodType;
    private String participantType;

    public UserEvent(Long userId, Long eventId, Short accepted, String participantType, String foodType) {
        this.userId = userId;
        this.eventId = eventId;
        this.accepted = accepted;
        this.foodType = foodType;
        this.participantType = participantType;
    }

    public UserEvent(Long userId, Long eventId, Short accepted) {
        this.userId = userId;
        this.eventId = eventId;
        this.accepted = accepted;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public Short getAccepted() {
        return accepted;
    }

    public String getFoodType() {
        return foodType;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setAccepted(Short accepted) {
        this.accepted = accepted;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public void setParticipantType(String participantType) {
        this.participantType = participantType;
    }
}
