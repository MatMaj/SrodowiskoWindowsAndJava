package impls;

import interfaces.UserDAO;
import models.Event;
import models.User;
import models.BaseConf;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private Boolean isSuccessful;
    public BaseConf baseConf = new BaseConf();

    @Override
    public Boolean registerUser(String name, String surname, String email, String login, String password) {
        isSuccessful = false;
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql = "INSERT INTO users VALUES(NULL,"+"'"+name+"',"+"'"+surname+"',"+"'"+login+"',"+"'"+password+"',"+"'"+email+"',"+"'User',NOW())";
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            t.commit();
            isSuccessful=true;
        }catch (HibernateException e) {
            e.printStackTrace();
            isSuccessful=false;
        }finally {
            return isSuccessful;
        }
    }

    @Override
    public Optional<User> loginUser(String login, String password) {
        Optional<User> user;
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql = "SELECT * FROM users WHERE login="+"'"+login+"' AND password="+"'"+password+"'";
            SQLQuery query = session.createSQLQuery(sql);
            user=null;
            t.commit();
        }

        /*try (Connection connection = getConnection()) {
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
        }*/
        return user;
    }

    @Override
    public Boolean checkLogin(String login) {
        isSuccessful = false;
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql = "SELECT * FROM users WHERE login="+"'"+login+"'";
            SQLQuery query = session.createSQLQuery(sql);
            if(!query.getResultList().isEmpty()){
                isSuccessful=true;
            }
            t.commit();
        }finally {
            return isSuccessful;
        }
    }

    @Override
    public Boolean checkEmail(String email) {
        isSuccessful = false;
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql = "SELECT * FROM users WHERE email="+"'"+email+"'";
            SQLQuery query = session.createSQLQuery(sql);
            if(!query.getResultList().isEmpty()){
                isSuccessful=true;
            }
            t.commit();
        }finally {
            return isSuccessful;
        }
    }

    @Override
    public Boolean checkUserId(Long id) {
        isSuccessful = false;
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql = "SELECT * FROM users WHERE id="+id;
            SQLQuery query = session.createSQLQuery(sql);
            if(!query.getResultList().isEmpty()){
                isSuccessful=true;
            }
            t.commit();
        }finally {
            return isSuccessful;
        }
    }

    @Override
    public void deleteUser(Long id) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql = "DELETE FROM users WHERE id="+id;
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            t.commit();
        }
    }

    @Override
    public void resetPassword(Long id, String password) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            String sql = "UPDATE users SET password="+ "'"+ password+ "'" + " WHERE id="+id;
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            t.commit();
        }
    }

    @Override
    public List<User> getUsers() {
        try(Session session = baseConf.getSessionFactory().openSession()){
            List<User> user = session.createCriteria(User.class).list();
            return user;
        }
    }

    @Override
    public void addUser(User user) {
        try(Session session = baseConf.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            user.setId(0L);
            session.save(user);
            t.commit();
        }
    }

    @Override
    public User getNewestUser() {
        User user = null;
        try(Session session = baseConf.getSessionFactory().openSession()) {
            Query q = session.createQuery("FROM User u ORDER BY u.id DESC");
            user = (User) q.setFirstResult(0).list().get(0);
        }
        return user;
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
