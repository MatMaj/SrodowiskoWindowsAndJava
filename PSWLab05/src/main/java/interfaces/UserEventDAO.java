package interfaces;

import models.UserEvent;

import java.util.ArrayList;
import java.util.List;

public interface UserEventDAO {

    List<UserEvent> getAllUserEvent();

    void acceptUserInEvent(UserEvent userEvent);

    void acceptEveryUser();

    Boolean checkUserInEvent(UserEvent userEvent);

    Boolean addUserToEvent(UserEvent userEvent);

    Boolean deleteUserFromEvent(UserEvent userEvent);

    Boolean deleteAllUser(Long userId);

    Boolean deleteAllEvent(Long eventId);

    Boolean checkConnection();
}
