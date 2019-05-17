package interfaces;

import models.User;

import java.util.ArrayList;
import java.util.Optional;

public interface UserDAO {
    Boolean registerUser(String name, String secondName, String email, String login, String password);

    Optional<User> loginUser(String login, String password); //return name of user

    Boolean checkLogin(String login);

    Boolean checkEmail(String login);

    Boolean checkConnection();

    void deleteUser(Long id);

    void resetPassword(Long id, String password);

    Boolean checkUserId(Long id);

    ArrayList<User> getUsers();

    void addUser(User user);

    User getNewestUser();
}
