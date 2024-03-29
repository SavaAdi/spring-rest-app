package com.adisavaspring.rest.springrestapp.services;

import com.adisavaspring.rest.springrestapp.model.persisted.UserEntity;
import com.adisavaspring.rest.springrestapp.repositories.UserRepository;
import com.adisavaspring.rest.springrestapp.shared.UserDto;
import com.adisavaspring.rest.springrestapp.shared.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final Utils utils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.utils = utils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        UserEntity storedUser = userRepository.save(userEntity);

        UserDto returnedUser = new UserDto();
        BeanUtils.copyProperties(storedUser, returnedUser);

        return returnedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity userEntity = userRepository.findUserByEmail(email);

       if(userEntity == null)
           throw new UsernameNotFoundException(email);

       return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        UserDto returnedUser = new UserDto();
        BeanUtils.copyProperties(userEntity, returnedUser);

        if(userEntity == null)
            throw new UsernameNotFoundException(email);

        return returnedUser;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UsernameNotFoundException(userId);

        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {

        UserDto updatedUser = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UsernameNotFoundException(userId);

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        UserEntity updatedEntity = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedEntity, updatedUser);

        return updatedUser;
    }

    @Override
    public void deleteUser(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UsernameNotFoundException(userId);

        userRepository.delete(userEntity);
    }
}
