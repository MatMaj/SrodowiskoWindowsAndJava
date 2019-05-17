package impls;

import interfaces.UserDAO;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private Boolean isSuccessful;

    @Override
    public Boolean registerUser(String name, String surname, String email, String login, String password) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("INSERT INTO users " + "VALUES (NULL,?,?,?,?,?,'User',NOW())");
            ((PreparedStatement) statement).setString(1, name);
            ((PreparedStatement) statement).setString(2, surname);
            ((PreparedStatement) statement).setString(3, login);
            ((PreparedStatement) statement).setString(4, password);
            ((PreparedStatement) statement).setString(5, email);
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    @Override
    public Optional<User> loginUser(String login, String password) {
        Optional<User> user = Optional.empty();
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM users WHERE login=? AND password=?");
            ((PreparedStatement) statement).setString(1, login);
            ((PreparedStatement) statement).setString(2, password);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                user = Optional.of(new User(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("rights")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }

    @Override
    public Boolean checkLogin(String login) {
        isSuccessful = false;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM users WHERE login=?");
            ((PreparedStatement) statement).setString(1, login);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.isBeforeFirst()) {
                isSuccessful = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    @Override
    public Boolean checkEmail(String email) {
        isSuccessful = false;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM users WHERE email=?");
            ((PreparedStatement) statement).setString(1, email);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.isBeforeFirst()) {
                isSuccessful = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    @Override
    public Boolean checkUserId(Long id) {
        isSuccessful = false;
        try (Connection connection = getConnection()){
            Statement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            ((PreparedStatement) statement).setLong(1, id);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.isBeforeFirst()) {
                isSuccessful = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    @Override
    public Boolean checkConnection() {
        isSuccessful = false;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginapp?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "zaq1@WSX")) {
            isSuccessful = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    @Override
    public void deleteUser(Long id) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            ((PreparedStatement) statement).setLong(1, id);
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        }
    }

    @Override
    public void resetPassword(Long id, String password) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("UPDATE users SET password=? WHERE id=?");
            ((PreparedStatement) statement).setString(1, password);
            ((PreparedStatement) statement).setLong(2, id);
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String email = resultSet.getString("email");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String rights = resultSet.getString("rights");
                Date date = resultSet.getDate("date");
                users.add(new User(id, name, surname, email, login, password, rights, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    @Override
    public void addUser(User user) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("INSERT INTO users " + "VALUES (NULL,?,?,?,?,?,?,?)");
            ((PreparedStatement) statement).setString(1, user.getName());
            ((PreparedStatement) statement).setString(2, user.getSurname());
            ((PreparedStatement) statement).setString(3, user.getLogin());
            ((PreparedStatement) statement).setString(4, user.getPassword());
            ((PreparedStatement) statement).setString(5, user.getEmail());
            ((PreparedStatement) statement).setString(6, user.getRights());
            ((PreparedStatement) statement).setDate(7, user.getDate());
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        }
    }

    @Override
    public User getNewestUser() {
        User user = null;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM users ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String email = resultSet.getString("email");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String rights = resultSet.getString("rights");
                Date date = resultSet.getDate("date");
                user = new User(id, name, surname, email, login, password, rights, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginapp?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "zaq1@WSX");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return connection;
        }
    }
}
