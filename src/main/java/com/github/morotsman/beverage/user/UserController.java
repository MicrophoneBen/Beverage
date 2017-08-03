package com.github.morotsman.beverage.user;

import java.security.Principal;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class UserController { 

    final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/user")
    public BeverageUserDto createUser(@RequestBody @Valid BeverageUserDto user) {
        return userService.createUser(user);
    }

    
    @GetMapping("/user")  
    public BeverageUserDto getUser(Principal principal) {
        return new BeverageUserDto(null, principal.getName(),null);
    }
    

}
