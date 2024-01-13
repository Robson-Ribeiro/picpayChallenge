package com.picpayChallenge.dtos;

public class ExceptionDto {
    private String message;
    private String statusCode;

    public ExceptionDto(String message, String statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
