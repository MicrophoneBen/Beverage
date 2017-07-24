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
public class BeverageLoginTest {

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
        RestTester.get()
                .withUrl(baseUrl)
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
    }

    
    private RestTester login(final String username, final String password) {
        return RestTester.get()
                .withUrl(baseUrl)
                .withCredentials(username, password)
                .expectedStatus(HttpStatus.OK);
    }
    
    

}
