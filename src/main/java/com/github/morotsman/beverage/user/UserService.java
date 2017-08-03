package com.github.morotsman.beverage.user;


public interface UserService {
    
    BeverageUserDto createUser(BeverageUserDto user);
    
    void deleteAllUsers();
   
    
}
