package com.github.morotsman.beverage.user;

import java.security.Principal;
import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class UserController { 

    final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/v1/user")
    @ResponseStatus(HttpStatus.CREATED)
    public BeverageUserDto createUser(@RequestBody @Valid BeverageUserDto user) {
        return userService.createUser(user);
    }

    
    @GetMapping("/user")  
    public BeverageUserDto getUser(Principal principal) {
        return new BeverageUserDto(null, principal.getName(),null);
    }
    
    
    //TODO add log
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class })
    public void handleException(Exception e) throws Exception {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);
        throw e;
    }
    
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ EntityExistsException.class })
    public ErrorDto handleException(EntityExistsException e) throws Exception {
        return new ErrorDto(e.getMessage());  
    }    
    
      

}
