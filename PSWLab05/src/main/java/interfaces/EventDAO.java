package interfaces;

import models.Event;

import java.util.ArrayList;

public interface EventDAO {

    Boolean addEvent(Event event);

    Boolean deleteEvent(Long id);

    Boolean modifyEvent(Event event);

    ArrayList<Event> getEvents();

    Boolean checkEventId(Long id);

    Event getNewestEvent();

    Boolean checkConnection();

}
