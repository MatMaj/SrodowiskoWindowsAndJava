package impls;

import interfaces.EventDAO;
import models.BaseConf;
import models.Event;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.List;

public class EventDAOImpl implements EventDAO {
    private Boolean isSuccessful;
    public BaseConf baseConf = new BaseConf();
    @Override
    public Event addEvent(Event event) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            session.save(event);
            t.commit();
            return event;
        }
    }

    @Override
    public void deleteEvent(Long id) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            Event event = new Event(id);
            session.delete(event);
            t.commit();
        }
    }

    @Override
    public void modifyEvent(Event event) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            session.update(event);
            t.commit();
        }
    }

    @Override
    public List<Event> getEvents() {
        try(Session session = baseConf.getSessionFactory().openSession()){
            List<Event> events = session.createCriteria(Event.class).list();
            return events;
        }
    }

    @Override
    public List<Event> checkEventId(Long id) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Query q = session.createQuery("FROM Event e WHERE e.id=:id").setLong("id",id);
            return q.list();
        }
    }

    @Override
    public Event getNewestEvent(){
        Event event = null;
        try(Session session = baseConf.getSessionFactory().openSession()) {
            Query q = session.createQuery("FROM Event e ORDER BY e.id DESC");
            event = (Event) q.setFirstResult(0).list().get(0);
        }
        return event;
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
}
