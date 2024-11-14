package com.nwbproj.primes.service.impl;

import com.nwbproj.primes.service.AlgorithimsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AlgorithimsServiceImpl implements AlgorithimsService {
    @Override

    public List<Integer> defaultAlgorithm(Integer n) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 1; i<= n ; i++) {
            if (isPrime(i)){
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public List<Integer> sieveOfEratosthenes(Integer n) {
        int srt = (int) Math.sqrt(n.doubleValue());
        ArrayList<Integer> result = new ArrayList<>();

        //Create array of values up to n+2 and make them all true then set 0 and 1 to be not prime.
        //if a value is false it is not a prime
        boolean[] prime = new boolean[n + 2];
        Arrays.fill(prime, true);
        prime[0] = false;
        prime[1] = false;

        for (int p = 2; p * p <= n; p++) {
            // If prime[p] is not changed, then it is a prime
            if (prime[p]) {
                // Update all multiples of p greater than or equal to the square of it numbers which
                // are multiple of p and are less than p^2 are already been marked.
                for (int i = p * p; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }

        // Print all prime numbers
        for (int p = srt; p <= n; p++) {
            if (prime[p]) {
                result.add(p);
            }
        }
        return result;
    }

    //default algorithm. works by knowing all even numbers are not primes except 2
    private boolean isPrime(int n){
        if (n == 0 || n == 1){
            return false;
        }

        if (n == 2){
            return true;
        }

        for (int i = 2; i * i <= n ; i++) {
            if (n%i==0){
                return false;
            }
        }
        return true;
    }
}
