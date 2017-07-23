package com.github.morotsman.beverage;

import java.util.Arrays;
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

import java.util.Optional;
import java.util.function.Function;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;


public class RestTester {
    
    private final String password;
    
    private final String username;
    
    private final HttpStatus expectedHttpStatus;
    
    private final String url;
       
    private final String requestBody;
    
    private final String expectedBody;
    
    private final HttpMethod httpMethod;
    
    private final String baseUrl;
   
    
    
    public static RestTester get(final String baseUrl) {
        return new RestTester(HttpMethod.GET, baseUrl, null,null,null, null,null, null);
    }
    
    public static RestTester post(final String baseUrl) { 
        return new RestTester(HttpMethod.POST, baseUrl, null,null,null, null,null,null);
    }
    
    public static RestTester delete(final String baseUrl) {
        return new RestTester(HttpMethod.DELETE, baseUrl, null,null,null, null,null,null);
    }
    
    private RestTester(final HttpMethod httpMethod, final String baseUrl, final String url, final String username, final String password, 
            final HttpStatus expectedHttpStatus, final String expectedBody, final String requestBody) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.expectedHttpStatus = expectedHttpStatus;
        this.expectedBody = expectedBody;
        this.httpMethod = httpMethod;
        this.requestBody = requestBody;
        this.baseUrl = baseUrl;
    }
    
    public RestTester withUrl(final String url) {
       return new RestTester(httpMethod, baseUrl, url, username, password, expectedHttpStatus,expectedBody,requestBody);
    }
    
    public RestTester withCredentials(final String username, final String password) {
        return new RestTester(httpMethod, baseUrl, url, username, password, expectedHttpStatus,expectedBody,requestBody);
    }
    
    public RestTester withRequestBody(final String requestBody) {
        return new RestTester(httpMethod, baseUrl, url, username, password, expectedHttpStatus,expectedBody,requestBody);
    }
    
    public RestTester expectedStatus(HttpStatus httpStatus) {
        return new RestTester(httpMethod, baseUrl, url, username,password,httpStatus,expectedBody,requestBody);
    }
    
    public RestTester expectedBody(final String expectedBody) {
        return new RestTester(httpMethod, baseUrl, url, username,password,expectedHttpStatus,expectedBody,requestBody); 
    }
    
    public void assertCall() {
        RestTemplate template = restTemplate(username,password);
        
        HttpHeaders headers = new HttpHeaders();
        
        
        if(username != null) {
            ResponseEntity<String> login =  template.getForEntity(baseUrl,String.class);

            final Optional<String> xsrfToken = login.getHeaders().get("Set-Cookie").stream()
                    .filter(v -> v.startsWith("XSRF-TOKEN"))
                    .map(t -> t.split(";")[0].split("=")[1]).findFirst();
      
            headers.add("X-XSRF-TOKEN", xsrfToken.get());
            headers.add("Cookie","XSRF-TOKEN=" + xsrfToken.get());
        }
         
        try {
            
            
            if(requestBody != null) {
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            }
            
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody,headers);
            ResponseEntity<String> response = template.exchange(baseUrl + url, httpMethod, requestEntity, String.class);
            
            if(expectedHttpStatus != null) {
                assertEquals(expectedHttpStatus,response.getStatusCode());
            } 
            if(expectedBody != null) {
                assertEquals(expectedBody.replace(" ", ""),response.getBody().replace(" ", ""));
            }
        } catch(HttpClientErrorException e) {
            if(expectedHttpStatus!=null && expectedHttpStatus.value() >= 400) {
                assertEquals(expectedHttpStatus,e.getStatusCode());
            } else {
                throw e;
            }
        }      
    }
    
    private RestTemplate restTemplate(final String username, final String password) {

        final RestTemplate restTemplate = new RestTemplate();
        
        if(username == null || password == null) return restTemplate;

        restTemplate.setMessageConverters(Arrays.asList(
                new FormHttpMessageConverter(),
                new StringHttpMessageConverter()
        ));
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(username, password));


        return restTemplate;
    }
    
    
    


    
}
