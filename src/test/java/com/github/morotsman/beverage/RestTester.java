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
  
    
    private final HttpMethod httpMethod;
    
    private final Class<R> responseBodyClass;
    
    
    public static <R> RestTester<R> get(Class<R> responseBodyClass) {
        return new RestTester<>(responseBodyClass, HttpMethod.GET, null,null, null,null, null);
    }
    
    public static <R> RestTester<R> post(Class<R> responseBodyClass) { 
        return new RestTester<>(responseBodyClass, HttpMethod.POST, null,null, null,null,null);
    }
    
    public static <R> RestTester<R> delete(Class<R> responseBodyClass) {
        return new RestTester<>(responseBodyClass, HttpMethod.DELETE, null,null, null,null,null);
    }
    
    private RestTester(Class<R> responseBodyClass, final HttpMethod httpMethod, final String url, final String username, final String password, 
            final HttpStatus expectedHttpStatus, final String requestBody) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.expectedHttpStatus = expectedHttpStatus;
        this.httpMethod = httpMethod;
        this.requestBody = requestBody;
        this.responseBodyClass = responseBodyClass;
    }
    
    public RestTester<R> withUrl(final String url) {
       return new RestTester<>(responseBodyClass, httpMethod, url, username, password, expectedHttpStatus,requestBody);
    }
    
    public RestTester<R> withCredentials(final String username, final String password) {
        return new RestTester<>(responseBodyClass,httpMethod, url, username, password, expectedHttpStatus,requestBody);
    }
    
    public RestTester<R> withRequestBody(final String requestBody) {
        return new RestTester<>(responseBodyClass,httpMethod, url, username, password, expectedHttpStatus,requestBody);
    }
    
    public RestTester<R> expectedStatus(HttpStatus httpStatus) {
        return new RestTester<>(responseBodyClass,httpMethod, url, username,password,httpStatus,requestBody);
    }
    
    public ResponseEntity<R> assertCall(final RestTemplate template) {
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
            ResponseEntity<R> response = template.exchange(url, httpMethod, requestEntity, responseBodyClass);
            
            if(expectedHttpStatus != null) {
                assertEquals(expectedHttpStatus,response.getStatusCode());
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
