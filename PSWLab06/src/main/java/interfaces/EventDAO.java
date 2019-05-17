package interfaces;

import models.Event;

import java.util.ArrayList;
import java.util.List;

public interface EventDAO {

    Event addEvent(Event event);

    void deleteEvent(Long id);

    void modifyEvent(Event event);

    List<Event> getEvents();

    List<Event> checkEventId(Long id);

    Event getNewestEvent();

    Boolean checkConnection();

}
