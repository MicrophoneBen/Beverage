package com.github.morotsman.beverage.function_test;

import com.github.morotsman.beverage.test_util.RestTester;
import com.github.morotsman.beverage.rater.RateService;
import com.github.morotsman.beverage.user.BeverageUserDto;
import com.github.morotsman.beverage.user.UserService;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTest {

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
        RestTester.get(String.class).withUrl(baseUrl).assertCall(restTemplate);
    }

    @After
    public void after() {
        userService.deleteAllUsers();
    }

    @Test
    public void testCreateUser() {
        ResponseEntity<String> response = createUser("user4", "password", 23L)
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
       
    }

    
    @Test
    public void testCreateUserWithANonUniqueUserName() {
        createUser("user4", "password", 23L)
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);

        createUser("user4", "password", 23L)
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
    }

    @Test
    public void testCreateUserThatIs20() {
        createUser("user4", "password", 20L)
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
    }

    @Test
    public void testCreateUserThatIsYoungerThen20() {
        createUser("user4", "password", 19L)
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
    }

    @Test
    public void testCreateUserWithNoPassword() {
        createUser("user4", null, 100L)
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
    }
    
    @Test
    public void testCreateUserWithShortPassword() {
        createUser("user4", "passw", 100L)
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
    }
    
    public void testCreateUserWithNoUsername() {
        createUser(null, "password", 100L)
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
    }    

    @Test
    public void testCreateUserWithNoAge() {
        createUser("user4", "password", null)
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
    }
    

    public RestTester<String> createUser(final String username, final String password, final Long age) {
        final String body = Stream.of((username!=null?"\"username\": \"" + username + "\"":"")
                ,(password!=null?"\"password\": \"" + password + "\"":""),
                (age!=null?"\"age\":" + age:"")).filter(s -> s.length() > 0).collect(joining(",", "{","}"));
       
        
        return RestTester.put(String.class)
                .withRequestBody(body)
                .withUrl(baseUrl + "v1/user")
                .expectedStatus(HttpStatus.CREATED);
    }

}
