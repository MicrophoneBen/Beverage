package com.github.morotsman.beverage.user;

import org.hibernate.validator.constraints.NotEmpty;


public class BeverageUserDto {
    
    @NotEmpty
    final String password;
    
    @NotEmpty
    final String username;

    public BeverageUserDto(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "BeverageUserDto{" + "password=" + password + ", username=" + username + '}';
    }
    
    
    
    
    
    
}
