package org.example.library.client;

public class User {
    private String login;
    private String passwordHash;
    private String name;
    private UserRole role;

    public User(String login, String passwordHash, String name, UserRole role) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.name = name;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
