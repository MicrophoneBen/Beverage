package com.github.morotsman.beverage;

import java.nio.charset.Charset;
import java.util.Collections;
import static org.junit.Assert.assertEquals;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.springframework.security.crypto.codec.Base64;


public class RestTester<R> {
    
    private final String password;
    
    private final String username;
    
    private final HttpStatus expectedHttpStatus;
    
    private final String url;
       
    private final String requestBody;
    
    private final String expectedBody;
    
    private final HttpMethod httpMethod;
    
    
    
    public static RestTester get() {
        return new RestTester(HttpMethod.GET, null,null,null, null,null, null);
    }
    
    public static RestTester post() { 
        return new RestTester(HttpMethod.POST, null,null,null, null,null,null);
    }
    
    public static RestTester delete() {
        return new RestTester(HttpMethod.DELETE, null,null,null, null,null,null);
    }
    
    private RestTester(final HttpMethod httpMethod, final String url, final String username, final String password, 
            final HttpStatus expectedHttpStatus, final String expectedBody, final String requestBody) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.expectedHttpStatus = expectedHttpStatus;
        this.expectedBody = expectedBody;
        this.httpMethod = httpMethod;
        this.requestBody = requestBody;
    }
    
    public RestTester withUrl(final String url) {
       return new RestTester(httpMethod, url, username, password, expectedHttpStatus,expectedBody,requestBody);
    }
    
    public RestTester withCredentials(final String username, final String password) {
        return new RestTester(httpMethod, url, username, password, expectedHttpStatus,expectedBody,requestBody);
    }
    
    public RestTester withRequestBody(final String requestBody) {
        return new RestTester(httpMethod, url, username, password, expectedHttpStatus,expectedBody,requestBody);
    }
    
    public RestTester expectedStatus(HttpStatus httpStatus) {
        return new RestTester(httpMethod, url, username,password,httpStatus,expectedBody,requestBody);
    }
    
    public RestTester expectedBody(final String expectedBody) {
        return new RestTester(httpMethod, url, username,password,expectedHttpStatus,expectedBody,requestBody); 
    }
    
    public ResponseEntity<String> assertCall(final RestTemplate template) {
        try {
            HttpHeaders headers = new HttpHeaders();
            
            if(username != null) {
                headers = getLoginHeaders(username,password);
            }
            
            if(requestBody != null) {
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            }
            
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody,headers);
            ResponseEntity<String> response = template.exchange(url, httpMethod, requestEntity, String.class);
            
            if(expectedHttpStatus != null) {
                assertEquals(expectedHttpStatus,response.getStatusCode());
            } 
            if(expectedBody != null) {
                assertEquals(expectedBody.replace(" ", ""),response.getBody().replace(" ", ""));
            }
            return response;
        } catch(HttpClientErrorException e) {
            if(expectedHttpStatus!=null && expectedHttpStatus.value() >= 400) {
                assertEquals(expectedHttpStatus,e.getStatusCode());
            } else {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        } 
        return null;
    }
    
    
    private HttpHeaders getLoginHeaders(final String username, final String password){
        HttpHeaders requestHeaders = new HttpHeaders();
        final String auth = username + ":" + password;
        final byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
        final String authHeader = "Basic " + new String(encodedAuth);
        requestHeaders.set("Authorization", authHeader);
        return requestHeaders;
    }
    
    
    


    
}
