package org.example.server.logic.users;

public class User {
    private UserRole role;
    private String username;
    private String password;
    public User() {
    }

    public User(UserRole role, String username, String password, String instrument) {
        this.role = role;
        this.username = username;
        this.password = password;
    }
    // GETS
    public UserRole getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    // SETS

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    // TO STRING


    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
