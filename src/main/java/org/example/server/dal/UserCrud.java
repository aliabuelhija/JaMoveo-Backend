package org.example.server.dal;

import org.example.server.data.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserCrud extends CrudRepository<UserEntity, String> {

}