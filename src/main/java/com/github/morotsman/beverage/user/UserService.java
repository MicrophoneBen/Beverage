package com.github.morotsman.beverage.user;


public interface UserService {
    
    BeverageUserDto createUser(BeverageUserDto user);
    
    BeverageUserDto getUser(final String username);
    
    void deleteAllUsers();
   
    
}
