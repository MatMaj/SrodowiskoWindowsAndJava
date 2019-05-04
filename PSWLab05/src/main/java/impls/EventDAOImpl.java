package impls;

import models.Event;

import java.sql.*;
import java.util.ArrayList;

public class EventDAOImpl {
    private Boolean isSuccessful;

    public Boolean addEvent(Event event) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("INSERT INTO events VALUES(NULL,?,?,?)");
            ((PreparedStatement) statement).setString(1, event.getName());
            ((PreparedStatement) statement).setString(2, event.getAgenda());
            ((PreparedStatement) statement).setDate(3, event.getDate());
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    public Boolean deleteEvent(Long id) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            ((PreparedStatement) statement).setLong(1, id);
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    public Boolean modifyEvent(Event event) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("UPDATE users SET name=?, agenda=?, date=? WHERE id=?");
            ((PreparedStatement) statement).setString(1, event.getName());
            ((PreparedStatement) statement).setString(2, event.getAgenda());
            ((PreparedStatement) statement).setDate(3, event.getDate());
            ((PreparedStatement) statement).setLong(4, event.getId());
            ((PreparedStatement) statement).execute();
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    public ArrayList<Event> getEvents() {
        ArrayList<Event> events = new ArrayList<Event>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM events");
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String agenda = resultSet.getString("agenda");
                Date date = resultSet.getDate("date");
                events.add(new Event(id, name, agenda, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return events;
        }
    }

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

    public Boolean checkEventId(Long id) {
        isSuccessful = false;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            ((PreparedStatement) statement).setLong(1, id);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.isBeforeFirst()){
                isSuccessful = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }
    }

    public Event getNewestEvent(){
        Event event = null;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM events ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String agenda = resultSet.getString("agenda");
                Date date = resultSet.getDate("date");
                event = new Event(id, name, agenda, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return event;
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
