package com.picpayChallenge.ExceptionHandlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.picpayChallenge.dtos.ExceptionDto;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity generic(Exception exception) {
        System.out.println(11111);
        return ResponseEntity.badRequest().body(new ExceptionDto(exception.getMessage(), "500"));
    }
    
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity userRepeatsUniqueCredential(DataIntegrityViolationException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDto("Your credential are already in use!", "400"));
    }
}
