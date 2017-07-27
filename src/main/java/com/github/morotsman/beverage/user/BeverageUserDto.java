package com.github.morotsman.beverage.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


public class BeverageUserDto {
    
    @NotEmpty
    private String password;
    
    @NotEmpty
    private String username;
    
    @NotNull
    @Min(20)
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
