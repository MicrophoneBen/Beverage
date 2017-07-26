package com.github.morotsman.beverage.rater;

import com.github.morotsman.beverage.model.Rate;
import com.github.morotsman.beverage.rater.service.UnknownRateException;
import com.github.morotsman.beverage.rater.service.WrongUserException;
import java.security.Principal;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/rate")
public class RateController {
    
    private final RateService rateService;
    
    public RateController(final RateService rateService) {
        this.rateService = rateService;
    }
    
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Rate> getRates(Principal principal) {
        return rateService.getRates(principal.getName());
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RateDto createRate(@RequestBody @Valid RateDto rate, Principal principal) {
        return rateService.createRate(principal.getName(),rate);
    }
    
    
    @RequestMapping(value = "/{rateId}", method = RequestMethod.GET)
    public RateDto getRate(@PathVariable long rateId, Principal principal) {
        System.out.println("Get rate");
        return rateService.getRate(principal.getName(),rateId);
    }
    
    @RequestMapping(value = "/{rateId}", method = RequestMethod.PUT)
    public RateDto updateRate(@PathVariable long rateId,@RequestBody @Valid RateDto rate, Principal principal) {
        return rateService.updateRate(principal.getName(),rate);
    }
    
    @RequestMapping(value = "/{rateId}", method = RequestMethod.DELETE)
    public void deleteRate(@PathVariable long rateId, Principal principal) {
        rateService.deleteRate(principal.getName(),rateId);  
    }
    
    //TODO add log
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class })
    public void handleException(Exception e) throws Exception {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);
        throw e;
    }
    
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({ EmptyResultDataAccessException.class})
    public void handleException(EmptyResultDataAccessException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);
        
    }
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({ UnknownRateException.class})
    public void handleException(UnknownRateException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);
        
    } 
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({JpaObjectRetrievalFailureException.class})
    public void handleException(JpaObjectRetrievalFailureException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);
        
    }
    
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public void handleException(MethodArgumentNotValidException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);
        
    }
    
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ DataIntegrityViolationException.class })
    public void handleException(DataIntegrityViolationException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);    
    }
    
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler({ WrongUserException.class })
    public void handleException(WrongUserException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);    
    }
    
    
    

    
    
}
