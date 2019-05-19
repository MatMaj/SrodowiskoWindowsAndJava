package models;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class BaseConf {
    public SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/loginapp?useLegacyDatetimeCode=false&serverTimezone=UTC");
        properties.setProperty("hibernate.connection.username", "root");
        properties.setProperty("hibernate.connection.password", "root");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.c3p0.acquire_increrment","1");
        properties.setProperty("hibernate.c3p0.idle_test_period","100");
        properties.setProperty("hibernate.c3p0.max_size","10");
        properties.setProperty("hibernate.c3p0.max_statements","10");
        properties.setProperty("hibernate.c3p0.min_size","10");
        properties.setProperty("hibernate.c3p0.timeout","100");
        Configuration configuration = new Configuration().addProperties(properties);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Event.class);
        configuration.addAnnotatedClass(UserEvent.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        SessionFactory sessionFactory = configuration
                .buildSessionFactory(serviceRegistry);

        return sessionFactory;
    }
}
