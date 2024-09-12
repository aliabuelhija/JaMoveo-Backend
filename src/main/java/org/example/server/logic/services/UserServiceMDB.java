package org.example.server.logic.services;

import org.example.server.dal.UserCrud;
import org.example.server.data.UserEntity;
import org.example.server.logic.UsersService;
import org.example.server.logic.converters.UsersConverter;
import org.example.server.logic.exceptions.ConflictException;
import org.example.server.logic.exceptions.BadRequestException;
import org.example.server.logic.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceMDB implements UsersService {

    private UserCrud userCrud;
    private UsersConverter userConverter;
    @Autowired
    public UserServiceMDB(UsersConverter userConverter, UserCrud userCrud) {
        this.userConverter = userConverter;
        this.userCrud = userCrud;
    }
    @Transactional
    @Override
    public User register(User user) {

        // Check if the fields are valid
        if (!CheckFields.checkFieldsToCreateUser(user)) {
            throw new BadRequestException("The fields for user are not valid");

        }
        Optional<UserEntity> existingUser = userCrud.findById(user.getUsername());

        if (existingUser.isPresent()) {
            throw new ConflictException("User is already signed");
        }


        UserEntity userEntity = this.userConverter.userToEntity(user);
        userEntity = this.userCrud.save(userEntity);
        return this.userConverter.entityToUser(userEntity);

    }

    @Override
    public User login(User user) {
        Optional<UserEntity> userEntityOptional = userCrud.findById(user.getUsername());

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if (userEntity.getPassword().equals(user.getPassword())) {
                return this.userConverter.entityToUser(userEntity);
            }
        }

        return null;
    }


}
