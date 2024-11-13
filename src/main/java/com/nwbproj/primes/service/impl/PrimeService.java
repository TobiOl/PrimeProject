package com.nwbproj.primes.service.impl;

import com.nwbproj.primes.model.PrimesResponse;
import org.springframework.http.ResponseEntity;

public interface PrimeService {

    public ResponseEntity<PrimesResponse> calculatePrimeList(Integer number, String algorithm) throws Exception;
}
