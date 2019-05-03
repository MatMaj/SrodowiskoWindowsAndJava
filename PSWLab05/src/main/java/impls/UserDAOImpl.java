package impls;

import interfaces.UserDAO;
import models.User;

import java.sql.*;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private Boolean isSuccessful;

    public Boolean registerUser(String name, String secondName, String email, String login, String password) {
        isSuccessful = true;
        try (Connection connection = getConnection()){
            Statement statement = connection.prepareStatement("INSERT INTO userdatabase " + "VALUES (NULL,?,?,?,?,?,'User',NOW())");
            ((PreparedStatement) statement).setString(1, name);
            ((PreparedStatement) statement).setString(2, secondName);
            ((PreparedStatement) statement).setString(3, login);
            ((PreparedStatement) statement).setString(4, password);
            ((PreparedStatement) statement).setString(5, email);
            ((PreparedStatement) statement).execute();
        } catch (SQLException e){
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    public Optional<User> loginUser(String login, String password){
        Optional<User> user = Optional.empty();
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM userdatabase WHERE login=? AND haslo=?");
            ((PreparedStatement) statement).setString(1, login);
            ((PreparedStatement) statement).setString(2, password);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.isBeforeFirst()){
                resultSet.next();
                user = Optional.of(new User(resultSet.getString("name"),
                                resultSet.getString("secoundName"),
                                resultSet.getString("uprawnienia")));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }

    public Boolean checkLogin(String login){
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM userdatabase WHERE login=?");
            ((PreparedStatement) statement).setString(1, login);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.isBeforeFirst()){
                isSuccessful = true;
            } else {
                isSuccessful = false;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    public Boolean checkEmail(String email){
        isSuccessful = true;
        try (Connection connection = getConnection()){
            Statement statement = connection.prepareStatement("SELECT * FROM userdatabase WHERE email=?");
            ((PreparedStatement) statement).setString(1, email);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.isBeforeFirst()){
                isSuccessful = true;
            } else {
                isSuccessful = false;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    private Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginapp?useLegacyDatetimeCode=false&serverTimezone=UTC","root","zaq1@WSX");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            return connection;
        }
    }

    public Boolean checkConnection(){
        isSuccessful = false;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginapp?useLegacyDatetimeCode=false&serverTimezone=UTC","root","zaq1@WSX")){
            isSuccessful = true;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }
}
