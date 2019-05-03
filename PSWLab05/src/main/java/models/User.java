package models;

public class User {
    private String name;
    private String secondName;
    private String email;
    private String login;
    private String password;
    private String rights;

    public User(String name, String secondName, String email, String login, String password) {
        this.name = name;
        this.secondName = secondName;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public User(String name, String secondName, String rights){
        this.name = name;
        this.secondName = secondName;
        this.rights = rights;
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

    public String getSecondName() {
        return secondName;
    }

    public String getPassword() {
        return password;
    }
}
