package com.nwbproj.primes.exceptions;

public class InvalidPrimeInputException extends BaseException{
    public InvalidPrimeInputException(String message, Integer httpStatus) {
        super(message, httpStatus);
    }
}
