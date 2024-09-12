package org.example.server.logic;

import org.example.server.data.UserEntity;
import org.example.server.logic.users.User;
import org.springframework.http.ResponseEntity;

public interface  UsersService {
    public User register(User user);
    public User login(User user);


    }
