package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="users")
public class User {
    @Id
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String login;
    private String password;
    private String rights;
    private Date date;


    public User(Long id, String name, String surname, String email, String login, String password, String rights, Date date) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.rights = rights;
        this.date = date;
    }

    public User(String name, String surname, String email, String login, String password, String rights, Date date) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.rights = rights;
        this.date = date;
    }

    public User(String name, String surname, String email, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(Long id, String name, String surname, String rights) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.rights = rights;
    }

    public User(){

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getRights() {
        return rights;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
