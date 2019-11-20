package com.adisavaspring.rest.springrestapp.controllers;

import com.adisavaspring.rest.springrestapp.exceptions.UserServiceException;
import com.adisavaspring.rest.springrestapp.model.request.UserDetailsRequestModel;
import com.adisavaspring.rest.springrestapp.model.response.UserRest;
import com.adisavaspring.rest.springrestapp.services.UserService;
import com.adisavaspring.rest.springrestapp.shared.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UserRest getUsers(@PathVariable String id){

        UserRest userRest = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, userRest);

        return userRest;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{

        if(userDetails.getEmail().isEmpty() || userDetails.getFirstName().isEmpty())
            throw new UserServiceException("Please fill Email and first name");

        UserRest returnedValue = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnedValue);

        return returnedValue;
    }

    @PutMapping
    public String updateUser(){
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }
}
