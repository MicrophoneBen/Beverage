package com.github.morotsman.beverage.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;


public class BeverageUserDto {
    
    @Size(min = 6, message="The password must be longer the 20 characters.")
    @NotEmpty
    private String password;
    
    @NotEmpty(message="The username must not be empty")
    private String username;
    
    @NotNull
    @Min(value = 20, message="The user must be older then 20 years.")
    private Long age;
    
    protected BeverageUserDto(){}

    public BeverageUserDto(String password, String username, Long age) {
        this.password = password;
        this.username = username;
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Long getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "BeverageUserDto{" + "password=" + password + ", username=" + username + ", age=" + age + '}';
    }   
    
}
