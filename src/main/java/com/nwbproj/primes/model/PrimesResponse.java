package com.nwbproj.primes.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class PrimesResponse {
    private List<Integer> numbers;
}
