package interfaces;

import models.UserEvent;

import java.util.ArrayList;

public interface UserEventDAO {

    ArrayList<UserEvent> getAllUserEvent();

    ArrayList<UserEvent> getUserEvents(Long userId);

    Boolean acceptUserInEvent(UserEvent userEvent);

    Boolean acceptEveryUser();

    Boolean checkUserInEvent(UserEvent userEvent);

    Boolean addUserToEvent(UserEvent userEvent);

    Boolean deleteUserFromEvent(UserEvent userEvent);

    Boolean deleteAllUser(Long userId);

    Boolean deleteAllEvent(Long eventId);

    Boolean checkConnection();
}
