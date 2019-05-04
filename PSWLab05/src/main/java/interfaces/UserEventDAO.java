package interfaces;

public interface UserEventDAO {

    Boolean checkUserInEvent();

    Boolean addUserToEvent();

    Boolean removeUserFromEvent();

    Boolean checkConnection();
}
