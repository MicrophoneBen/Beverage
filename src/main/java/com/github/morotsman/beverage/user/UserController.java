package com.github.morotsman.beverage.user;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    
    final UserService userService;
    
    public UserController(final UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("")
    public BeverageUserDto createUser(@RequestBody @Valid BeverageUserDto user) {
        return userService.createUser(user);
    }
    
    @GetMapping("/{userName}")
    public BeverageUserDto createUser(final @PathVariable String userName) {
        return userService.getUser(userName);
    }
    
}
