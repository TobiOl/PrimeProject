package com.nwbproj.primes.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrimesResponse {
    private List<Integer> numbers;
}
