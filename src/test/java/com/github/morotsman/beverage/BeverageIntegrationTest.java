package com.github.morotsman.beverage;

import java.util.function.Function;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BeverageIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private String baseUrl;

    @Before
    public void before() {
        baseUrl = "http://localhost:" + port + "/";
    }

    private final Function<String,Function<String,RestTester>> login = username -> password -> {
        return RestTester.get(baseUrl)
                .withUrl("")
                .withCredentials(username, password);
    };
    
    @Test
    public void testLogin() {
        login.apply("user1").apply("password")
                .expectedStatus(HttpStatus.OK)
                .assertCall();
    }

    public void loginWithWrongCredentials() {
        login.apply("unknown_user").apply("password")
                .expectedStatus(HttpStatus.UNAUTHORIZED)
                .assertCall();
    }

    public RestTester createRate(final String body) {
        return RestTester.post(baseUrl)
                .withUrl("v1/rate")
                .withCredentials("user1", "password")
                .withRequestBody("{\"description\": \"ghhg\", \"rate\": 7, \"productId\": 3}")
                .expectedStatus(HttpStatus.OK);
    }

    @Test
    public void testCreateRate() {
        createRate("{\"description\": \"ghhg\", \"rate\": 7, \"productId\": 3}")
                .expectedBody("{\"rateId\":1,\"description\": \"ghhg\", \"rate\": 7, \"productId\": 3}")
                .assertCall();

        RestTester.delete(baseUrl)
                .withUrl("v1/rate/1")
                .withCredentials("user1", "password")
                .expectedStatus(HttpStatus.OK)
                .assertCall();
    }

    @Test
    public void deleteRate() {
        RestTester.delete(baseUrl)
                .withUrl("v1/rate/1")
                .withCredentials("user1", "password")
                .expectedStatus(HttpStatus.OK)
                .assertCall();

        RestTester.get(baseUrl)
                .withUrl("v1/rate")
                .withCredentials("user1", "password")
                .expectedStatus(HttpStatus.OK)
                .expectedBody("[]")
                .assertCall();
    }

    @Test
    public void listRatesWhenNoRatesExists() {
        RestTester.get(baseUrl)
                .withUrl("v1/rate")
                .withCredentials("user1", "password")
                .expectedStatus(HttpStatus.OK)
                .expectedBody("[]")
                .assertCall();
    }

}
