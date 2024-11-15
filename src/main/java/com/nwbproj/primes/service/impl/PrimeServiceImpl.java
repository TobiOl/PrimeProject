package com.nwbproj.primes.service.impl;

import com.nwbproj.primes.enums.Algorithms;
import com.nwbproj.primes.exceptions.InvalidAlgorithimException;
import com.nwbproj.primes.exceptions.InvalidPrimeInputException;
import com.nwbproj.primes.model.PrimesResponse;
import com.nwbproj.primes.service.AlgorithimsService;
import com.nwbproj.primes.service.PrimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable("primeCache")
    public ResponseEntity<PrimesResponse> calculatePrimeList(Integer number, Algorithms algorithm) throws Exception {
        PrimesResponse response = new PrimesResponse();

        if (number<0){
            throw new InvalidPrimeInputException("Values below 1 are invalid", HttpStatus.BAD_REQUEST.value());
        }
        if (algorithm==null){
            throw new InvalidAlgorithimException("Algorithm value cannot be null", HttpStatus.BAD_REQUEST.value());
        }

        switch (algorithm){
            //TODO consider some kind of concurrent algorithm for sieve
            case DEFAULT -> response.setNumbers(algorithimsService.defaultAlgorithm(number));
            case SIEVE_OF_ERATHOSTENES -> response.setNumbers(algorithimsService.sieveOfEratosthenes(number));
            default -> throw new InvalidAlgorithimException("Algorithm entered not found", HttpStatus.NOT_FOUND.value());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
