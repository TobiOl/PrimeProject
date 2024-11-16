package com.nwbproj.primes.endpoints;


import com.nwbproj.primes.enums.Algorithms;
import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public class PrimesIT {




    @BeforeEach
    public void setUp(){
        String baseURL = StringUtils.join("http://localhost:8080","/getPrime/");
        RestAssured.baseURI = baseURL;
    }

    @Test
    @DisplayName("Retrieves short list of primes")
    public void GivenPrimeInput100AndDefaultAlgorithm_ShouldReturnResponse(){
        given()
                .contentType("application/json")
                .param("algorithm", Algorithms.DEFAULT)
                .get("100")
                .then()
                .assertThat()
                .body("numbers", instanceOf(ArrayList.class))
                .statusCode(HttpStatus.OK.value());
    }


    @Test
    @DisplayName("Retrieves short list of primes (SoE)")
    public void GivenPrimeInput100AndSoEAlgorithm_ShouldReturnResponse(){
        given()
                .contentType("application/json")
                .param("algorithm", Algorithms.SIEVE_OF_ERATHOSTENES)
                .get("100")
                .then()
                .assertThat()
                .body("numbers", instanceOf(ArrayList.class))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Retrieves short list of primes (XML)")
    public void GivenPrimeInput100AndDefaultAlgorithm_ShouldReturnResponseXML(){
        given()
                .contentType("application/xml")
                .param("algorithm", Algorithms.DEFAULT)
                .get("100")
                .then()
                .assertThat()
                .body("numbers", instanceOf(ArrayList.class))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Returns 400 if no algorithm is given")
    public void GivenNoAlgorithm_ShouldReturn400(){
        given()
                .contentType("application/json")
                .get("100")
                .then()
                .assertThat()
                .body("detail", equalTo("Algorithm value cannot be null"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Returns 400 if value below 0 is given")
    public void GivenValueBelow1_ShouldReturn400(){
        given()
                .contentType("application/json")
                .param("algorithm", Algorithms.DEFAULT)
                .get("-200")
                .then()
                .assertThat()
                .body("detail", equalTo("Values below 0 are invalid"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }




}
