package com.adisavaspring.rest.springrestapp.controllers;

import com.adisavaspring.rest.springrestapp.exceptions.UserServiceException;
import com.adisavaspring.rest.springrestapp.model.request.UserDetailsRequestModel;
import com.adisavaspring.rest.springrestapp.model.response.UserRest;
import com.adisavaspring.rest.springrestapp.services.UserService;
import com.adisavaspring.rest.springrestapp.shared.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.CREATED)
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

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value = "/{id}",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE } )
    public UserRest updateUser(@PathVariable String id ,@RequestBody UserDetailsRequestModel userDetails){

        UserRest returnedValue = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);
        UserDto updatedUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updatedUser, returnedValue);

        return returnedValue;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE } )
    public String deleteUser(@PathVariable String id){
        
        String userId = userService.getUserByUserId(id).getUserId();
        userService.deleteUser(id);
        return "User " + userId + " was deleted";
    }
}
