package com.nwbproj.primes.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class BaseException extends Exception{

    private Integer httpStatus;

    public BaseException(String message, Integer httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public BaseException(String message){
        super(message);
    }

}
