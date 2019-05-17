package impls;

import interfaces.UserEventDAO;
import models.UserEvent;

import java.sql.*;
import java.util.ArrayList;

public class UserEventDAOImpl implements UserEventDAO {
    private Boolean isSuccessful;

    @Override
    public ArrayList<UserEvent> getAllUserEvent() {
        ArrayList<UserEvent> userEvents = new ArrayList<>();
        try (Connection connection = getConnection()){
            Statement statement = connection.prepareStatement("SELECT * FROM user_event");
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                Long eventId = resultSet.getLong("event_id");
                Short accepted = resultSet.getShort("accepted");
                String participantType = resultSet.getString("participant");
                String foodType = resultSet.getString("food");
                userEvents.add(new UserEvent(userId, eventId, accepted, participantType, foodType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return userEvents;
        }
    }

    @Override
    public ArrayList<UserEvent> getUserEvents(Long userId) {
        ArrayList<UserEvent> userEvents = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM user_event WHERE user_id=?");
            ((PreparedStatement) statement).setLong(1, userId);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            while (resultSet.next()) {
                Long eventId = resultSet.getLong("event_id");
                Short accepted = resultSet.getShort("accepted");
                String participantType = resultSet.getString("participant");
                String foodType = resultSet.getString("food");
                userEvents.add(new UserEvent(userId, eventId, accepted, participantType, foodType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return userEvents;
        }
    }

    @Override
    public Boolean acceptUserInEvent(UserEvent userEvent) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("UPDATE user_event SET accepted=? WHERE user_id=? and event_id=?");
            ((PreparedStatement) statement).setShort(1, userEvent.getAccepted());
            ((PreparedStatement) statement).setLong(2, userEvent.getUserId());
            ((PreparedStatement) statement).setLong(3, userEvent.getEventId());
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    @Override
    public Boolean acceptEveryUser() {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("UPDATE user_event SET accepted=1 WHERE accepted=0");
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    @Override
    public Boolean checkUserInEvent(UserEvent userEvent) {
        isSuccessful = false;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM user_event WHERE user_id=? AND event_id=?");
            ((PreparedStatement) statement).setLong(1, userEvent.getUserId());
            ((PreparedStatement) statement).setLong(2, userEvent.getEventId());
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
    public Boolean addUserToEvent(UserEvent userEvent) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("INSERT INTO user_event VALUES(?,?,?,?,?)");
            ((PreparedStatement) statement).setLong(1, userEvent.getUserId());
            ((PreparedStatement) statement).setLong(2, userEvent.getEventId());
            ((PreparedStatement) statement).setShort(3, userEvent.getAccepted());
            ((PreparedStatement) statement).setString(4, userEvent.getParticipantType());
            ((PreparedStatement) statement).setString(5, userEvent.getFoodType());
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    @Override
    public Boolean deleteUserFromEvent(UserEvent userEvent) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("DELETE FROM user_event WHERE user_id=? AND event_id=?");
            ((PreparedStatement) statement).setLong(1, userEvent.getUserId());
            ((PreparedStatement) statement).setLong(2, userEvent.getEventId());
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    @Override
    public Boolean deleteAllUser(Long userId) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("DELETE FROM user_event WHERE user_id=?");
            ((PreparedStatement) statement).setLong(1, userId);
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    @Override
    public Boolean deleteAllEvent(Long eventId) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("DELETE FROM user_event WHERE event_id=?");
            ((PreparedStatement) statement).setLong(1, eventId);
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
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

