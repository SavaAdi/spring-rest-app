package com.adisavaspring.rest.springrestapp.exceptions;

import com.adisavaspring.rest.springrestapp.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {UserServiceException.class, HttpServerErrorException.InternalServerError.class})
    public ResponseEntity<Object> handleUserServiceException(UserServiceException exp, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(new Date(), exp.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
