package com.medullus.fabricrestapi.exceptions;

import io.swagger.models.auth.In;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value  = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException{


    private static final long serialVersionUID = 6601846480415020218L;
    private final String message;
    public InternalServerErrorException(String message){
        this.message = message;
    }
}
