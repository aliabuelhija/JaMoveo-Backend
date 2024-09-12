package org.example.server.controllers;


import org.example.server.logic.services.UserServiceMDB;
import org.example.server.logic.exceptions.BadRequestException;
import org.example.server.logic.users.User;
import org.example.server.logic.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAPIController {
    UserServiceMDB userService;


    @Autowired
    public UserAPIController(UserServiceMDB userService) {
        this.userService = userService;
    }
    @PostMapping("/user")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (user.getRole() == UserRole.ADMIN) {
            throw new BadRequestException("Cannot register as admin through user signup");
        }

        User registeredUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
          return userService.login(user) ;

    }
}
