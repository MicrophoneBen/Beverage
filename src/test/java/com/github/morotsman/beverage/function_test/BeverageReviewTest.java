package com.github.morotsman.beverage.function_test;

import com.github.morotsman.beverage.test_util.RestTester;
import com.github.morotsman.beverage.review.ReviewDto;
import com.github.morotsman.beverage.review.ReviewService;
import com.github.morotsman.beverage.user.BeverageUserDto;
import com.github.morotsman.beverage.user.UserService;
import org.junit.After;
import org.junit.Assert;
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
public class BeverageReviewTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    @Qualifier("CsrfRestTemplate")
    private RestTemplate restTemplate;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ReviewService rateService; 

    private String baseUrl;

    @Before
    public void before() {  
        baseUrl = "http://localhost:" + port + "/"; 
        createUser("user1", "password",20L);
        createUser("user2", "password", 30L);
    }
    
    @After
    public void after() {
        userService.deleteAllUsers();
    }
    
    @Test
    public void testCreateReview() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<ReviewDto> review = createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        Assert.assertEquals(new ReviewDto(review.getBody().getReviewId(), "a description", 7L, 3L, review.getBody().getName(),review.getBody().getProducer()), review.getBody());
    }
    
    @Test
    public void testThatItIsImpossibleToReviewTheSameBeverageTwoTimes() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<ReviewDto> review = createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        createReview("{\"description\": \"a description\",\"review\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        assertThatTheNumberOfReviewsAre(1);
    }    
    
     @Test
    public void testThatItIsPossibleToReviewTheSameBeverageTwoTimesIfTheUsersAreDifferent() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<ReviewDto> review = createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        assertThatTheNumberOfReviewsAre(1);
        
        login("user2", "password").assertCall(restTemplate);
        
        createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        assertThatTheNumberOfReviewsAre(1);
    }     
    
    @Test
    public void testCreateWithInvalidInputs() {
        login("user1", "password").assertCall(restTemplate);
        
        
        createReview("{\"description\": \"a description\",\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createReview("{\"description\": \"a description\",\"rate\": -1,\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createReview("{\"description\": \"a description\",\"rate\": 11,\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createReview("{\"description\": \"a description\",\"rate\": 7}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":-1}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":7438437843}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createReview(null)
                .expectedStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .assertCall(restTemplate);
        
        assertThatTheNumberOfReviewsAre(0);
    }   
    
    @Test
    public void testGetReview() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<ReviewDto> review = createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        ResponseEntity<ReviewDto> actual = getReview(review.getBody().getReviewId()).expectedStatus(HttpStatus.OK).assertCall(restTemplate);
        
        Assert.assertEquals(new ReviewDto(review.getBody().getReviewId(), "a description", 7L, 3L,review.getBody().getName(),review.getBody().getProducer()), actual.getBody());
    }
    
    
    @Test
    public void testGetNonExistingReview() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<ReviewDto> actual = getReview(1212211221L).expectedStatus(HttpStatus.NOT_FOUND).assertCall(restTemplate);
    }  
    
     @Test
    public void testDeleteNonExistingReview() {
        login("user1", "password").assertCall(restTemplate);
        
        deleteReview(1212211221L).expectedStatus(HttpStatus.NOT_FOUND).assertCall(restTemplate);
    } 
    
    @Test
    public void testUpdateNonExistingReview() {
        login("user1", "password").assertCall(restTemplate);
        
        updateReview(1212211221L,"{\"reviewId\":1212211221, \"description\": \"another description\",\"rate\": 3,\"productId\":1}")
                .expectedStatus(HttpStatus.NOT_FOUND).assertCall(restTemplate);
    } 
    
    @Test
    public void testGetAnotherUsersReview() {
        login("user1", "password").assertCall(restTemplate);      
        ResponseEntity<ReviewDto> review = createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        login("user2", "password").assertCall(restTemplate);
        ResponseEntity<ReviewDto> actual = getReview(review.getBody().getReviewId()).expectedStatus(HttpStatus.NOT_FOUND).assertCall(restTemplate);
    }
     
    @Test
    public void testDeleteReview() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<ReviewDto> review = createRandomReview().assertCall(restTemplate);
        
        assertThatTheNumberOfReviewsAre(1);

        deleteReview(review.getBody().getReviewId()).assertCall(restTemplate);
        
        assertThatTheNumberOfReviewsAre(0);
    }  
   
    
    @Test
    public void testDeleteAnotherUsersReview() {
        login("user1", "password").assertCall(restTemplate);       
        ResponseEntity<ReviewDto> review = createRandomReview().assertCall(restTemplate);
        assertThatTheNumberOfReviewsAre(1);
        
        login("user2", "password").assertCall(restTemplate);
        deleteReview(review.getBody().getReviewId())
                .expectedStatus(HttpStatus.NOT_FOUND)
                .assertCall(restTemplate);
        
        login("user1", "password").assertCall(restTemplate);
        assertThatTheNumberOfReviewsAre(1);
    }

    @Test
    public void listRatesWhenNoReviewsExists() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<ReviewDto[]> reviews = getReviews().assertCall(restTemplate);
        
        Assert.assertEquals(0,reviews.getBody().length);
    }
    
    @Test
    public void listAReview() {
        login("user1", "password").assertCall(restTemplate);
        createRandomReview().assertCall(restTemplate);
        
        assertThatTheNumberOfReviewsAre(1);
    }
    
    @Test
    public void verifyThatReviewsAreDefaultSortOnUpdatedTimeStamp() {
        login("user1", "password").assertCall(restTemplate);
        
        createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":1}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":5}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        assertThatTheNumberOfReviewsAre(3);
        
        ResponseEntity<ReviewDto[]> rates = getReviews().assertCall(restTemplate);
        Assert.assertEquals("A bear 5",rates.getBody()[0].getName());
        Assert.assertEquals("A bear 1",rates.getBody()[1].getName());
        Assert.assertEquals("A bear 3",rates.getBody()[2].getName());
    }
    
    @Test
    public void doNotListAnotherUsersReviews() {    
        login("user1", "password").assertCall(restTemplate);
        createRandomReview().assertCall(restTemplate);
        assertThatTheNumberOfReviewsAre(1);
              
        login("user2", "password").assertCall(restTemplate);
        assertThatTheNumberOfReviewsAre(0); 
    } 
 
    @Test
    public void updateAReview() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<ReviewDto> review = createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        Assert.assertEquals(new ReviewDto(review.getBody().getReviewId(), "a description", 7L, 3L, review.getBody().getName(), review.getBody().getProducer()), review.getBody());
        
        review = updateReview(review.getBody().getReviewId(), "{\"reviewId\":" + review.getBody().getReviewId() + ", \"description\": \"another description\",\"rate\": 3,\"productId\":1}")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
        
        Assert.assertEquals(new ReviewDto(review.getBody().getReviewId(), "another description", 3L, 3L, review.getBody().getName(), review.getBody().getProducer()), review.getBody());     
    }
   
    @Test
    public void doNotUpdateAnotherUsersReview() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<ReviewDto> review = createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.CREATED)
                .assertCall(restTemplate);
        
        Assert.assertEquals(new ReviewDto(review.getBody().getReviewId(), "a description", 7L, 3L, review.getBody().getName(), review.getBody().getProducer()), review.getBody());
        
        login("user2", "password").assertCall(restTemplate);
        
        updateReview(review.getBody().getReviewId(), 
                "{\"reviewId\":" + review.getBody().getReviewId() + ", \"description\": \"another description\",\"rate\": 3,\"productId\":1}")
                .expectedStatus(HttpStatus.NOT_FOUND)
                .assertCall(restTemplate);
        
        login("user1", "password").assertCall(restTemplate);      
        review = getReview(review.getBody().getReviewId()).assertCall(restTemplate);      
        Assert.assertEquals(new ReviewDto(review.getBody().getReviewId(), "a description", 7L, 3L, review.getBody().getName(), review.getBody().getProducer()), review.getBody());     
    }
    
    @Test
    public void testUpdateWithInvalidInputs() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<ReviewDto> review = createReview("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .assertCall(restTemplate);
        
        updateReview(review.getBody().getReviewId(), 
                "{\"reviewId\":" + review.getBody().getReviewId() + ", \"description\": \"another description\",\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        updateReview(review.getBody().getReviewId(), 
                "{\"reviewId\":" + review.getBody().getReviewId() + ", \"description\": \"another description\",\"rate\": 11,\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        updateReview(review.getBody().getReviewId(), 
                "{\"reviewId\":" + review.getBody().getReviewId() + ", \"description\": \"another description\",\"rate\": -1,\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        updateReview(review.getBody().getReviewId(), 
                "{\"reviewId\":" + review.getBody().getReviewId() + ", \"description\": \"another description\",\"rate\": 3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        
        updateReview(review.getBody().getReviewId(), 
                "{\"reviewId\":" + review.getBody().getReviewId() + ", \"description\": \"another description\",\"rate\": 3,\"productId\":1")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        review = getReview(review.getBody().getReviewId()).assertCall(restTemplate);      
        Assert.assertEquals(new ReviewDto(review.getBody().getReviewId(), "a description", 7L, 3L, review.getBody().getName(), review.getBody().getProducer()), review.getBody());
        
    } 
    
    
    private void assertThatTheNumberOfReviewsAre(final int expectedNumberOfRates) {
        ResponseEntity<ReviewDto[]> rates = getReviews().assertCall(restTemplate);
        Assert.assertEquals(expectedNumberOfRates,rates.getBody().length);
    }
    
    private RestTester<ReviewDto[]> getReviews() {
        return RestTester.get(ReviewDto[].class)
                .withUrl(baseUrl + "v1/review")
                .expectedStatus(HttpStatus.OK);
    }
    
    private RestTester<String> deleteReview(final Long id) {
        return RestTester.delete(String.class)
                .withUrl(baseUrl + "v1/review/" + id)
                .expectedStatus(HttpStatus.OK);
    }
    
    private RestTester<String> login(final String username, final String password) {
        return RestTester.get(String.class)
                .withUrl(baseUrl)
                .withCredentials(username, password)
                .expectedStatus(HttpStatus.OK);
    }
    
    public RestTester<ReviewDto> createReview(final String body) {
        return RestTester.post(ReviewDto.class)
                .withUrl(baseUrl + "v1/review")
                .withRequestBody(body)
                .expectedStatus(HttpStatus.CREATED);
    }
    
    public RestTester<ReviewDto> getReview(final Long id) {
        return RestTester.get(ReviewDto.class)
                .withUrl(baseUrl + "v1/review/" + id)
                .expectedStatus(HttpStatus.OK);
    }    
    
    public RestTester<ReviewDto> updateReview(final Long id, final String body) {
        return RestTester.put(ReviewDto.class)
                .withUrl(baseUrl + "v1/review/" + id)
                .withRequestBody(body)
                .expectedStatus(HttpStatus.OK);
    }
    
    public RestTester<ReviewDto> createRandomReview() {
        return RestTester.post(ReviewDto.class)
                .withUrl(baseUrl+ "v1/review")
                .withRequestBody("{\"description\": \"ghhg\", \"rate\": 5, \"productId\": 3}")
                .expectedStatus(HttpStatus.CREATED);
    }
    
    public void createUser(final String username, final String password, final Long age) {
        userService.createUser(new BeverageUserDto(password,username, age));
    }
    
 
    
}
