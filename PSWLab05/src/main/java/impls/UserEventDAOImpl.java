package impls;

import interfaces.UserEventDAO;
import models.BaseConf;
import models.Event;
import models.UserEvent;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.List;

public class UserEventDAOImpl implements UserEventDAO {
    private Boolean isSuccessful;
    public BaseConf baseConf=new BaseConf();

    @Override
    public List<UserEvent> getAllUserEvent() {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Query q = session.createQuery("FROM UserEvent ev");
            List<UserEvent> userEvent = q.list();
            return userEvent;
        }
    }

    @Override
    public void acceptUserInEvent(UserEvent userEvent) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("UPDATE UserEvent ev SET ev.accepted=:accepted WHERE ev.user_id=:user_id and ev.event_id=:event_id")
                    .setShort("accepted",userEvent.getAccepted()).setLong("user_id",userEvent.getUser_id())
                    .setLong("event_id",userEvent.getEvent_id());
            q.executeUpdate();
            t.commit();
        }
    }

    @Override
    public void acceptEveryUser() {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql="UPDATE user_event SET accepted=1 WHERE accepted=0";
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            t.commit();
        }
    }

    @Override
    public Boolean checkUserInEvent(UserEvent userEvent) {
        isSuccessful = false;
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            Query q=session.createQuery("FROM UserEvent e WHERE e.user_id=:user_id and e.event_id=:event_id")
                    .setLong("user_id",userEvent.getUser_id()).setLong("event_id",userEvent.getUser_id());
            t.commit();
            if(q.list().isEmpty()){
                isSuccessful = true;
            }
        }
        return isSuccessful;
        /*isSuccessful = false;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM user_event WHERE user_id=? AND event_id=?");
            ((PreparedStatement) statement).setLong(1, userEvent.getUser_id());
            ((PreparedStatement) statement).setLong(2, userEvent.getEvent_id());
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.isBeforeFirst()) {
                isSuccessful = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return isSuccessful;
        }*/
    }

    @Override
    public Boolean addUserToEvent(UserEvent userEvent) {
        isSuccessful = true;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("INSERT INTO user_event VALUES(?,?,?,?,?,?)");
            ((PreparedStatement) statement).setLong(1, 0);
            ((PreparedStatement) statement).setLong(2, userEvent.getUser_id());
            ((PreparedStatement) statement).setLong(3, userEvent.getEvent_id());
            ((PreparedStatement) statement).setShort(4, userEvent.getAccepted());
            ((PreparedStatement) statement).setString(5, userEvent.getParticipant());
            ((PreparedStatement) statement).setString(6, userEvent.getFood());
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
            ((PreparedStatement) statement).setLong(1, userEvent.getUser_id());
            ((PreparedStatement) statement).setLong(2, userEvent.getEvent_id());
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
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginapp?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root")) {
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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginapp?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return connection;
        }
    }
}

