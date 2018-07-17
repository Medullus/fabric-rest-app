package com.medullus.fabricrestapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    private static final long serialVersionUID = -5915290911308498422L;
    private final String message;

    public BadRequestException(String message){
        this.message = message;
    }
}
