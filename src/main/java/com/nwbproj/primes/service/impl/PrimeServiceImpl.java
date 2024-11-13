package com.nwbproj.primes.service.impl;

import com.nwbproj.primes.model.PrimesResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class PrimeServiceImpl implements PrimeService{
    @Override
    public ResponseEntity<PrimesResponse> calculatePrimeList(Integer number, String algorithm) throws Exception {
        if (number<0){
            // TODO fix custom exception
            throw new Exception();
        }
        if (algorithm==null){
            throw new IllegalArgumentException("Algorithm cannot be null");
        }


        return null;
    }

    private Boolean isPrime (Integer n){
        if(n < 2) return false;
        if(n == 2 || n == 3) return true;
        if(n%2 == 0 || n%3 == 0) return false;
        long sqrtN = (long)Math.sqrt(n)+1;
        for(long i = 6L; i <= sqrtN; i += 6) {
            if(n%(i-1) == 0 || n%(i+1) == 0) return false;
        }
        return true;
    }
}
