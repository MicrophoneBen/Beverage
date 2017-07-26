package com.github.morotsman.beverage;

import com.github.morotsman.beverage.model.Rate;
import com.github.morotsman.beverage.rater.RateDto;
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
public class BeverageRateTest {

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
    public void testCreateRate() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<RateDto> rate = createRate("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
        
        Assert.assertEquals(new RateDto(rate.getBody().getRateId(), "a description", 7L, 3L), rate.getBody());

        deleteRate(rate.getBody().getRateId()).assertCall(restTemplate);
    }
    
    
    @Test
    public void testCreateWithInvalidInputs() {
        login("user1", "password").assertCall(restTemplate);
        
        
        createRate("{\"description\": \"a description\",\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createRate("{\"description\": \"a description\",\"rate\": -1,\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createRate("{\"description\": \"a description\",\"rate\": 11,\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createRate("{\"description\": \"a description\",\"rate\": 7}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createRate("{\"description\": \"a description\",\"rate\": 7,\"productId\":-1}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createRate("{\"description\": \"a description\",\"rate\": 7,\"productId\":7438437843}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        createRate(null)
                .expectedStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .assertCall(restTemplate);
        
        ResponseEntity<Rate[]> rates = getRates().assertCall(restTemplate);  
        Assert.assertEquals(0,rates.getBody().length);
    }   
    
    @Test
    public void testGetRate() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<RateDto> rate = createRate("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
        
        ResponseEntity<RateDto> actual = getRate(rate.getBody().getRateId()).expectedStatus(HttpStatus.OK).assertCall(restTemplate);
        
        Assert.assertEquals(new RateDto(rate.getBody().getRateId(), "a description", 7L, 3L), actual.getBody());
        
        deleteRate(rate.getBody().getRateId()).assertCall(restTemplate);
    }
    
    
    @Test
    public void testGetNonExistingRate() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<RateDto> actual = getRate(1212211221L).expectedStatus(HttpStatus.NOT_FOUND).assertCall(restTemplate);
    }  
    
     @Test
    public void testDeleteNonExistingRate() {
        login("user1", "password").assertCall(restTemplate);
        
        deleteRate(1212211221L).expectedStatus(HttpStatus.NOT_FOUND).assertCall(restTemplate);
    } 
    
    @Test
    public void testUpdateNonExistingRate() {
        login("user1", "password").assertCall(restTemplate);
        
        updateRate(1212211221L,"{\"rateId\":1212211221, \"description\": \"another description\",\"rate\": 3,\"productId\":1}")
                .expectedStatus(HttpStatus.NOT_FOUND).assertCall(restTemplate);
    }  
    
    @Test
    public void testGetAnotherUsersRate() {
        login("user1", "password").assertCall(restTemplate);      
        ResponseEntity<RateDto> rate = createRate("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
        
        login("user2", "password").assertCall(restTemplate);
        ResponseEntity<RateDto> actual = getRate(rate.getBody().getRateId()).expectedStatus(HttpStatus.FORBIDDEN).assertCall(restTemplate);
        
        login("user1", "password").assertCall(restTemplate);
        deleteRate(rate.getBody().getRateId()).assertCall(restTemplate);
    }
     
    @Test
    public void testDeleteRate() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<RateDto> rate = createRandomRate().assertCall(restTemplate);
        
        assertThatTheNumberOfRatesIs(1);

        deleteRate(rate.getBody().getRateId()).assertCall(restTemplate);
        
        assertThatTheNumberOfRatesIs(0);
    }  
   
    
    @Test
    public void testDeleteAnotherUsersRate() {
        login("user1", "password").assertCall(restTemplate);       
        ResponseEntity<RateDto> rate = createRandomRate().assertCall(restTemplate);
        assertThatTheNumberOfRatesIs(1);
        
        login("user2", "password").assertCall(restTemplate);
        deleteRate(rate.getBody().getRateId())
                .expectedStatus(HttpStatus.FORBIDDEN)
                .assertCall(restTemplate);
        
        login("user1", "password").assertCall(restTemplate);
        assertThatTheNumberOfRatesIs(1);
        
        deleteRate(rate.getBody().getRateId())
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
    }

    @Test
    public void listRatesWhenNoRatesExists() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<Rate[]> rates = getRates().assertCall(restTemplate);
        
        Assert.assertEquals(0,rates.getBody().length);
    }
    
    @Test
    public void listARate() {
        login("user1", "password").assertCall(restTemplate);
        ResponseEntity<RateDto> rate =  createRandomRate().assertCall(restTemplate);
        
        assertThatTheNumberOfRatesIs(1);
        
        deleteRate(rate.getBody().getRateId()).assertCall(restTemplate);
    }
    
    @Test
    public void doNotListAnotherUsersRates() {    
        login("user1", "password").assertCall(restTemplate);
        ResponseEntity<RateDto> rate =  createRandomRate().assertCall(restTemplate);
              
        login("user2", "password").assertCall(restTemplate);
        assertThatTheNumberOfRatesIs(0);
        
        login("user1", "password").assertCall(restTemplate);   
        assertThatTheNumberOfRatesIs(1);
        
        deleteRate(rate.getBody().getRateId()).assertCall(restTemplate);
    } 
 
    @Test
    public void updateARate() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<RateDto> rate = createRate("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
        
        Assert.assertEquals(new RateDto(rate.getBody().getRateId(), "a description", 7L, 3L), rate.getBody());
        
        rate = updateRate(rate.getBody().getRateId(), "{\"rateId\":" + rate.getBody().getRateId() + ", \"description\": \"another description\",\"rate\": 3,\"productId\":1}")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
        
        Assert.assertEquals(new RateDto(rate.getBody().getRateId(), "another description", 3L, 1L), rate.getBody());

        deleteRate(rate.getBody().getRateId()).assertCall(restTemplate);        
    }
   
    @Test
    public void doNotUpdateAnotherUsersRate() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<RateDto> rate = createRate("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
        
        Assert.assertEquals(new RateDto(rate.getBody().getRateId(), "a description", 7L, 3L), rate.getBody());
        
        login("user2", "password").assertCall(restTemplate);
        
        updateRate(rate.getBody().getRateId(), 
                "{\"rateId\":" + rate.getBody().getRateId() + ", \"description\": \"another description\",\"rate\": 3,\"productId\":1}")
                .expectedStatus(HttpStatus.FORBIDDEN)
                .assertCall(restTemplate);
        
        login("user1", "password").assertCall(restTemplate);      
        rate = getRate(rate.getBody().getRateId()).assertCall(restTemplate);      
        Assert.assertEquals(new RateDto(rate.getBody().getRateId(), "a description", 7L, 3L), rate.getBody());
        
        deleteRate(rate.getBody().getRateId()).assertCall(restTemplate);        
    }
    
    @Test
    public void testUpdateWithInvalidInputs() {
        login("user1", "password").assertCall(restTemplate);
        
        ResponseEntity<RateDto> rate = createRate("{\"description\": \"a description\",\"rate\": 7,\"productId\":3}")
                .assertCall(restTemplate);
        
        updateRate(rate.getBody().getRateId(), 
                "{\"rateId\":" + rate.getBody().getRateId() + ", \"description\": \"another description\",\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        updateRate(rate.getBody().getRateId(), 
                "{\"rateId\":" + rate.getBody().getRateId() + ", \"description\": \"another description\",\"rate\": 11,\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        updateRate(rate.getBody().getRateId(), 
                "{\"rateId\":" + rate.getBody().getRateId() + ", \"description\": \"another description\",\"rate\": -1,\"productId\":3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        updateRate(rate.getBody().getRateId(), 
                "{\"rateId\":" + rate.getBody().getRateId() + ", \"description\": \"another description\",\"rate\": 3}")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        updateRate(rate.getBody().getRateId(), 
                "{\"rateId\":" + rate.getBody().getRateId() + ", \"description\": \"another description\",\"rate\": 3,\"productId\":-1}")
                .expectedStatus(HttpStatus.NOT_FOUND)
                .assertCall(restTemplate);
        
        updateRate(rate.getBody().getRateId(), 
                "{\"rateId\":" + rate.getBody().getRateId() + ", \"description\": \"another description\",\"rate\": 3,\"productId\":7438437843}")
                .expectedStatus(HttpStatus.NOT_FOUND)
                .assertCall(restTemplate);
        
        updateRate(rate.getBody().getRateId(), 
                "{\"rateId\":" + rate.getBody().getRateId() + ", \"description\": \"another description\",\"rate\": 3,\"productId\":1")
                .expectedStatus(HttpStatus.BAD_REQUEST)
                .assertCall(restTemplate);
        
        rate = getRate(rate.getBody().getRateId()).assertCall(restTemplate);      
        Assert.assertEquals(new RateDto(rate.getBody().getRateId(), "a description", 7L, 3L), rate.getBody());
        
    }      
    
    private void assertThatTheNumberOfRatesIs(final int expectedNumberOfRates) {
        ResponseEntity<Rate[]> rates = getRates().assertCall(restTemplate);
        Assert.assertEquals(expectedNumberOfRates,rates.getBody().length);
    }
    
    private RestTester<Rate[]> getRates() {
        return RestTester.get(Rate[].class)
                .withUrl(baseUrl + "v1/rate")
                .expectedStatus(HttpStatus.OK);
    }
    
    private RestTester<String> deleteRate(final Long id) {
        return RestTester.delete(String.class)
                .withUrl(baseUrl + "v1/rate/" + id)
                .expectedStatus(HttpStatus.OK);
    }
    
    private RestTester<String> login(final String username, final String password) {
        return RestTester.get(String.class)
                .withUrl(baseUrl)
                .withCredentials(username, password)
                .expectedStatus(HttpStatus.OK);
    }
    
    public RestTester<RateDto> createRate(final String body) {
        return RestTester.post(RateDto.class)
                .withUrl(baseUrl + "v1/rate")
                .withRequestBody(body)
                .expectedStatus(HttpStatus.OK);
    }
    
    public RestTester<RateDto> getRate(final Long id) {
        return RestTester.get(RateDto.class)
                .withUrl(baseUrl + "v1/rate/" + id)
                .expectedStatus(HttpStatus.OK);
    }    
    
    public RestTester<RateDto> updateRate(final Long id, final String body) {
        return RestTester.put(RateDto.class)
                .withUrl(baseUrl + "v1/rate/" + id)
                .withRequestBody(body)
                .expectedStatus(HttpStatus.OK);
    }
    
    public RestTester<RateDto> createRandomRate() {
        return RestTester.post(RateDto.class)
                .withUrl(baseUrl+ "v1/rate")
                .withRequestBody("{\"description\": \"ghhg\", \"rate\": 5, \"productId\": 3}")
                .expectedStatus(HttpStatus.OK);
    }
    
}
