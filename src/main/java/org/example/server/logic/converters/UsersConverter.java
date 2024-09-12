package org.example.server.logic.converters;

import org.example.server.data.UserEntity;
import org.example.server.logic.users.User;
import org.springframework.stereotype.Component;
@Component
public class UsersConverter {

    // Converts an Entity to a User
    public static User entityToUser(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        User user = new User();
        user.setRole(entity.getRole());
        user.setUsername(entity.getUsername());
        user.setPassword(entity.getPassword());
        return user;
    }

    // Converts a User to an Entity
    public static UserEntity userToEntity(User user) {
        if (user == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setRole(user.getRole());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        return entity;
    }
}
