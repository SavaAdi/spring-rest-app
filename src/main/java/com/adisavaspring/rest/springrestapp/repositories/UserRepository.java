package com.adisavaspring.rest.springrestapp.repositories;

import com.adisavaspring.rest.springrestapp.model.persisted.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findUserByEmail(String email);
    UserEntity findByUserId(String userId);
}
