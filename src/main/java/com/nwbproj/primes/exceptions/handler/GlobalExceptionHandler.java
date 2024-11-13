package com.nwbproj.primes.exceptions.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public GlobalExceptionHandler(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

  /*  @ExceptionHandler(value = Exception.class)
    public  ResponseEntity<String> handleGlobalExceptions(Exception ex){
        ErrorResponse.
                builder(ex.)
                .detail(ex.getLocalizedMessage())
                        .build()
        logException(Exception.class.getSimpleName(), );
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    private void  logException(String exceptionName, ErrorResponse errorResponse){
        String json = null;

        try {
            json = objectMapper.writeValueAsString(errorResponse);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        logger.error("Exception: " + exceptionName + "\nException details: "+json);
    }

}
