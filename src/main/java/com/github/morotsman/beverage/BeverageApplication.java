package com.github.morotsman.beverage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling   
public class BeverageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeverageApplication.class, args);
    } 
   
      

}
