package com.github.morotsman.beverage.common;

import java.util.Arrays;
import java.util.List;


public class ErrorDto {
   
    final List<ErrorDetails> errors;
    

    public ErrorDto(String message) {
        errors = Arrays.asList(new ErrorDetails(message));
    }

    public List<ErrorDetails> getErrors() {
        return errors;
    }

    
    
    
    public class ErrorDetails {
        final String defaultMessage;
        
        public ErrorDetails(String defaultMessage) {
            this.defaultMessage = defaultMessage;
        }

        public String getDefaultMessage() {
            return defaultMessage;
        }
        
        
        
    }
    
    
    
}
