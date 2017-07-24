package com.github.morotsman.beverage;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BeverageIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    @Qualifier("CsrfRestTemplate")
    private RestTemplate restTemplate;

    private String baseUrl;

    @Before
    public void before() {
        baseUrl = "http://localhost:" + port + "/";  
    }

/*
    @Test
    public void testCreateRate() {
        login("user1", "password").assertCall(restTemplate);
        
        createRate("{\"description\": \"ghhg\", \"rate\": 7, \"productId\": 3}")
                .expectedBody("{\"rateId\":1,\"description\": \"ghhg\", \"rate\": 7, \"productId\": 3}")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);

        deleteRate("1").assertCall(restTemplate);
    }

    @Test
    public void listRatesWhenNoRatesExists() {
        login("user1", "password").assertCall(restTemplate);
        
        RestTester.get(baseUrl)
                .withUrl("v1/rate")
                .expectedStatus(HttpStatus.OK)
                .expectedBody("[]")
                .assertCall(restTemplate);
    }
    */
    @Test
    public void doNotListAnotherUsersRates() {
        login("user1", "password").assertCall(restTemplate);
        
        createRandomRate().assertCall(restTemplate);
        
        login("user2", "password").assertCall(restTemplate);
        
        RestTester.get()
                .withUrl(baseUrl + "v1/rate")
                .expectedStatus(HttpStatus.OK)
                .expectedBody("[]")
                .assertCall(restTemplate);
        
        login("user1", "password").assertCall(restTemplate);
        
        deleteRate("1").assertCall(restTemplate);
        
        RestTester.get()
                .withUrl(baseUrl + "v1/rate")
                .expectedStatus(HttpStatus.OK)
                .expectedBody("[]")
                .assertCall(restTemplate);
    }   
    
    private RestTester deleteRate(final String id) {
        return RestTester.delete()
                .withUrl(baseUrl + "v1/rate/" + id)
                .expectedStatus(HttpStatus.OK);
    }
    
    private RestTester login(final String username, final String password) {
        return RestTester.get()
                .withUrl(baseUrl)
                .withCredentials(username, password)
                .expectedStatus(HttpStatus.OK);
    }
    
    public RestTester createRate(final String body) {
        return RestTester.post()
                .withUrl(baseUrl + "v1/rate")
                .withRequestBody(body)
                .expectedStatus(HttpStatus.OK);
    }
    
    public RestTester createRandomRate() {
        return RestTester.post()
                .withUrl(baseUrl+ "v1/rate")
                .withRequestBody("{\"description\": \"ghhg\", \"rate\": 5, \"productId\": 3}")
                .expectedStatus(HttpStatus.OK);
    }
    
}
