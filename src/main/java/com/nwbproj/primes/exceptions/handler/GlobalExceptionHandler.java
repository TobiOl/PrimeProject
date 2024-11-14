package com.nwbproj.primes.exceptions.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwbproj.primes.exceptions.InvalidAlgorithimException;
import com.nwbproj.primes.exceptions.InvalidPrimeInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    Map<Integer, BiFunction<String, String, ErrorResponse>> statusCodeMap = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public GlobalExceptionHandler(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value = InvalidAlgorithimException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidAlgorithmException(InvalidAlgorithimException exception) throws JsonProcessingException {

        JsonNode res = new ObjectMapper().readTree(exception.getLocalizedMessage());
        String detail = res.get("errorDescription").asText();

        ErrorResponse errorResponse = processStatusCode(detail, exception.getHttpStatus());

        logException(InvalidAlgorithimException.class.getSimpleName(), errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = InvalidPrimeInputException.class)
    public final ResponseEntity<ErrorResponse> handlePrimeInputException(InvalidPrimeInputException exception) throws JsonProcessingException {

        JsonNode res = new ObjectMapper().readTree(exception.getLocalizedMessage());
        String detail = res.get("errorDescription").asText();

        ErrorResponse errorResponse = processStatusCode(detail, exception.getHttpStatus());

        logException(InvalidAlgorithimException.class.getSimpleName(), errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException ex) throws JsonProcessingException {

        String responseBody = ex.getResponseBodyAsString();

        ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
        logException(HttpClientErrorException.class.getSimpleName(), errorResponse);

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());

    }

    private void  logException(String exceptionName, ErrorResponse errorResponse){
        String json = null;

        try {
            json = objectMapper.writeValueAsString(errorResponse);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        logger.error("Exception: {}\nException details: {}", exceptionName, json);
    }

    private ErrorResponse processStatusCode(String details, Integer statusCode){
        Function var4 = (Function) this.statusCodeMap.get(statusCode);
        return (ErrorResponse) var4.apply(details);
    }

}
