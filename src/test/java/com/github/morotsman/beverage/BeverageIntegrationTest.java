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

    @Test
    public void testLogin() {
        login("user1", "password")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
    }

    @Test
    public void loginWithWrongCredentials() {
        login("unknown_user", "password")
                .expectedStatus(HttpStatus.UNAUTHORIZED)
                .assertCall(restTemplate);
    }

    @Test
    public void callWithoutCredentials() {
        RestTester.get(baseUrl)
                .withUrl("")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
    }

    @Test
    public void testCreateRate() {
        login("user1", "password").assertCall(restTemplate);

        createRate("{\"description\": \"ghhg\", \"rate\": 7, \"productId\": 3}")
                .withCredentials("user1", "password")
                .expectedBody("{\"rateId\":1,\"description\": \"ghhg\", \"rate\": 7, \"productId\": 3}")
                .assertCall(restTemplate);

        RestTester.delete(baseUrl)
                .withUrl("v1/rate/1")
                .withCredentials("user1", "password")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
    }

    @Test
    public void testDeleteRate() {
        login("user1", "password").assertCall(restTemplate);
        
        RestTester.delete(baseUrl)
                .withUrl("v1/rate/1")
                .withCredentials("user1", "password")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);

        RestTester.get(baseUrl)
                .withUrl("v1/rate")
                .withCredentials("user1", "password")
                .expectedStatus(HttpStatus.OK)
                .expectedBody("[]")
                .assertCall(restTemplate);
    }

    @Test
    public void listRatesWhenNoRatesExists() {
        RestTester.get(baseUrl)
                .withUrl("v1/rate")
                .withCredentials("user1", "password")
                .expectedStatus(HttpStatus.OK)
                .expectedBody("[]")
                .assertCall(restTemplate);
    }
    
    private RestTester login(final String username, final String password) {
        return RestTester.get(baseUrl)
                .withUrl("")
                .withCredentials(username, password)
                .expectedStatus(HttpStatus.OK);
    }
    
    public RestTester createRate(final String body) {
        return RestTester.post(baseUrl)
                .withUrl("v1/rate")
                .withRequestBody(body)
                .expectedStatus(HttpStatus.OK);
    }

}
