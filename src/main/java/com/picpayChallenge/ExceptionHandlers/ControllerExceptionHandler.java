package com.picpayChallenge.ExceptionHandlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.picpayChallenge.dtos.ExceptionDto;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ExceptionDto userRepeatsUniqueCredential(DataIntegrityViolationException exception) {
        return new ExceptionDto("Your credential are already in use!", "400");
    }
}
