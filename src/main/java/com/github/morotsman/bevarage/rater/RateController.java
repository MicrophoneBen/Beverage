package com.github.morotsman.bevarage.rater;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rate")
public class RateController {
    
    
    @RequestMapping(value = "/hello_world", method = RequestMethod.GET)
    public String helloWorld() {
        return "hi6";
    }
}
