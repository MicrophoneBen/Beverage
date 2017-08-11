package com.github.morotsman.beverage.function_test;

import com.github.morotsman.beverage.model.product.Product;
import com.github.morotsman.beverage.product_catalog.ProductCatalogService;
import com.github.morotsman.beverage.test_util.RestTester;
import com.github.morotsman.beverage.user.BeverageUserDto;
import com.github.morotsman.beverage.user.UserService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "product_catalog.provider.systembolaget.url=http://localhost:1080/product_supplier")
public class ProductCatalogTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    @Qualifier("CsrfRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductCatalogService productCatalogService;
    
    private String baseUrl;


    @Before
    public void before() {
        createUser("user1", "password",20L);
        baseUrl = "http://localhost:" + port + "/";
    }

    @After
    public void after() {
        userService.deleteAllUsers();
    }

    @Test
    public void testLoadProductCatalog() throws InterruptedException {
        login("user1","password");
        
        
        ResponseEntity<Product[]> result = RestTester.get(Product[].class)
                .withUrl(baseUrl + "/v1/product_catalog?page=0&query=")
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
        
        Assert.assertEquals(9, result.getBody().length);
    }

    private void login(final String username, final String password) {
        RestTester.get(String.class)
                .withUrl(baseUrl)
                .withCredentials(username, password)
                .expectedStatus(HttpStatus.OK)
                .assertCall(restTemplate);
    }
    
    public void createUser(final String username, final String password, final Long age) {
        userService.createUser(new BeverageUserDto(password,username, age));
    }

}
