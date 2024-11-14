package com.nwbproj.primes.exceptions;

public class InvalidAlgorithimException extends BaseException{
    public InvalidAlgorithimException(String message, Integer httpStatus) {
        super(message, httpStatus);
    }
}
