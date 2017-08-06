package com.github.morotsman.beverage.function_test;

import com.github.morotsman.beverage.test_util.RestTester;
import com.github.morotsman.beverage.rater.RateService;
import com.github.morotsman.beverage.user.BeverageUserDto;
import com.github.morotsman.beverage.user.UserService;
import org.junit.After;
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
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RateService rateService;

    private String baseUrl;

    @Before
    public void before() {
        baseUrl = "http://localhost:" + port + "/";
        
        createUser("user1", "password",23L);
    }
    
    @After
    public void after() {
        userService.deleteAllUsers();
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
        RestTester.get(String.class)
                .withUrl(baseUrl)
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
    }
    
    @Test
    public void callGetRatesWithoutCredentials() {
        RestTester.get(String.class)
                .withUrl(baseUrl + "v1/rate")
                .expectedStatus(HttpStatus.UNAUTHORIZED)
                .assertCall(restTemplate);
    }   

    
    private RestTester login(final String username, final String password) {
        return RestTester.get(String.class)
                .withUrl(baseUrl)
                .withCredentials(username, password)
                .expectedStatus(HttpStatus.OK);
    }
    
    public void createUser(final String username, final String password, final Long age) {
        userService.createUser(new BeverageUserDto(password,username,age));
    }
    
    

}
