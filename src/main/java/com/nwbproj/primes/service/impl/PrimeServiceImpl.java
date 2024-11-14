package com.nwbproj.primes.service.impl;

import com.nwbproj.primes.exceptions.InvalidAlgorithimException;
import com.nwbproj.primes.exceptions.InvalidPrimeInputException;
import com.nwbproj.primes.model.PrimesResponse;
import com.nwbproj.primes.service.AlgorithimsService;
import com.nwbproj.primes.service.PrimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class PrimeServiceImpl implements PrimeService {

    @Autowired
    private AlgorithimsServiceImpl algorithimsService;


    @Override
    public ResponseEntity<PrimesResponse> calculatePrimeList(Integer number, String algorithm) throws Exception {
        PrimesResponse response = new PrimesResponse();

        if (number<0){
            // TODO fix custom exception
            throw new InvalidPrimeInputException("Values below 1 are invalid", HttpStatus.BAD_REQUEST.value());
        }
        if (algorithm==null){
            throw new IllegalArgumentException("Algorithm cannot be null");
        }

        switch (algorithm){
            case "" -> response.setNumbers(algorithimsService.defaultAlgorithm(number));
            case "soe" -> response.setNumbers(algorithimsService.sieveOfEratosthenes(number));
            default -> throw new InvalidAlgorithimException("Must have a valid Algorithim input", HttpStatus.BAD_REQUEST.value());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
