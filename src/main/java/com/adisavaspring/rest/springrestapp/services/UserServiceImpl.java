package com.adisavaspring.rest.springrestapp.services;

import com.adisavaspring.rest.springrestapp.model.persisted.UserEntity;
import com.adisavaspring.rest.springrestapp.repositories.UserRepository;
import com.adisavaspring.rest.springrestapp.shared.UserDto;
import com.adisavaspring.rest.springrestapp.shared.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final Utils utils;

    public UserServiceImpl(UserRepository userRepository, Utils utils) {
        this.userRepository = userRepository;
        this.utils = utils;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        UserEntity checkedEntity = userRepository.findUserByEmail(userDto.getEmail());

        if(checkedEntity != null)
            throw new RuntimeException("Email already in use");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        String publicUserId = utils.generateUserId(20);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword("testPassword");

        UserEntity storedUser = userRepository.save(userEntity);

        UserDto returnedUser = new UserDto();
        BeanUtils.copyProperties(storedUser, returnedUser);

        return returnedUser;
    }
}
