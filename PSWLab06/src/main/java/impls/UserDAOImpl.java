package impls;

import interfaces.UserDAO;
import models.User;
import models.BaseConf;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.List;

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
    public User loginUser(String login, String password) {
        User user = null;
        try(Session session = baseConf.getSessionFactory().openSession()) {
            Query q = session.createQuery("FROM User u WHERE u.login=:login AND u.password=:password").setString("login",login)
                    .setString("password",password);
            user = (User) q.setFirstResult(0).list().get(0);
        }
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
