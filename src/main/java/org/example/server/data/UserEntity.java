package org.example.server.data;

import org.example.server.logic.users.UserRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class UserEntity {

    @Field("role")
    private UserRole role;

    @Id
    @Field("username")
    private String username;

    @Field("password")
    private String password;

    // Constructors
    public UserEntity() {
    }

    public UserEntity( UserRole role, String username, String password, String instrument) {
        this.role = role;
        this.username = username;
        this.password = password;
    }


    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

