package org.example.server.logic.services;

import org.example.server.logic.users.User;
import org.example.server.logic.users.UserRole;

public class CheckFields {
    // Check fields to create user
    // Check if the input is valid user role
    public static boolean isValidUserRole(String input) {
        try {
            UserRole.valueOf(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean checkFieldsToCreateUser(User user) {
        if (user.getUsername() == null
                || user.getUsername().isBlank()
                || user.getPassword() == null
                || user.getPassword().isBlank()
                || !CheckFields.isValidUserRole(user.getRole().toString())
        )
            return false;
        return true;
    }

}
