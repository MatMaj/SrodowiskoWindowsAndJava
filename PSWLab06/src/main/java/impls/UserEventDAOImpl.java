package impls;

import interfaces.UserEventDAO;
import models.BaseConf;
import models.UserEvent;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.sql.Delete;

import java.sql.*;
import java.util.List;

public class UserEventDAOImpl implements UserEventDAO {
    private Boolean isSuccessful;
    public BaseConf baseConf = new BaseConf();

    @Override
    public List<UserEvent> getAllUserEvent() {
        try (Session session = baseConf.getSessionFactory().openSession()) {
            Query q = session.createQuery("FROM UserEvent ev");
            List<UserEvent> userEvent = q.list();
            return userEvent;
        }
    }

    @Override
    public void acceptUserInEvent(UserEvent userEvent) {
        try (Session session = baseConf.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("UPDATE UserEvent ev SET ev.accepted=:accepted WHERE ev.user_id=:user_id and ev.event_id=:event_id")
                    .setShort("accepted", userEvent.getAccepted()).setLong("user_id", userEvent.getUser_id())
                    .setLong("event_id", userEvent.getEvent_id());
            q.executeUpdate();
            t.commit();
        }
    }

    @Override
    public void acceptEveryUser() {
        try (Session session = baseConf.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            String sql = "UPDATE user_event SET accepted=1 WHERE accepted=0";
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            t.commit();
        }
    }

    @Override
    public Boolean checkUserInEvent(UserEvent userEvent) {
        try (Session session = baseConf.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            Criteria criteria = session.createCriteria(UserEvent.class);
            criteria.add(Restrictions.eq("user_id", userEvent.getUser_id()));
            criteria.add(Restrictions.eq("event_id", userEvent.getEvent_id()));
            criteria.setProjection(Projections.rowCount());
            long count = (Long) criteria.uniqueResult();
            t.commit();
            if (count == 0) {
                return true;
            } else {
                return false;
            }

        }
    }

    @Override
    public void addUserToEvent(UserEvent userEvent) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            userEvent.setId(0L);
            session.save(userEvent);
            t.commit();
        }
    }

    @Override
    public void deleteUserFromEvent(UserEvent userEvent) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql = "DELETE FROM user_event WHERE user_id="+userEvent.getUser_id()+" and event_id="+userEvent.getEvent_id();
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            t.commit();
        }
    }

    @Override
    public void deleteAllUser(Long userId) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql = "DELETE FROM user_event WHERE user_id="+userId;
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            t.commit();
        }
    }

    @Override
    public void deleteAllEvent(Long eventId) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql = "DELETE FROM user_event WHERE event_id="+eventId;
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            t.commit();
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

