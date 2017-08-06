package com.github.morotsman.beverage.rater;

import com.github.morotsman.beverage.common.ErrorDto;
import com.github.morotsman.beverage.model.exceptions.UnknownRateException;
import com.github.morotsman.beverage.model.exceptions.UnknownProductException;
import java.security.Principal;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/rate")
public class RateController {
    
    private final RateService rateService;
    
    public RateController(final RateService rateService) {
        this.rateService = rateService;
    }
    
    @GetMapping(value = "")
    public Iterable<RateDto> getRates(Principal principal, final @RequestParam(required=false) String query, final @RequestParam(required=false) Integer page) {
        return rateService.getRates(principal.getName(), query==null?"":query, page==null?0:page);
    }
     
    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public RateDto createRate(@RequestBody @Valid RateDto rate, Principal principal) {
        return rateService.createRate(principal.getName(),rate).orElseThrow(() -> new UnknownProductException());
    }
    
    
    @GetMapping(value = "/{rateId}")
    public RateDto getRate(@PathVariable long rateId, Principal principal) {
        return rateService
                .getRate(principal.getName(),rateId)
                .orElseThrow(() -> new UnknownRateException());
    }
    
    @PutMapping(value = "/{rateId}")
    public RateDto updateRate(@PathVariable long rateId,@RequestBody @Valid RateDto rate, Principal principal) {
        return rateService
                .updateRate(principal.getName(),rate)
                .orElseThrow(() -> new UnknownRateException());
    }
    
    @DeleteMapping(value = "/{rateId}")
    public void deleteRate(@PathVariable long rateId, Principal principal) {
        rateService
                .deleteRate(principal.getName(),rateId)
                .orElseThrow(() -> new UnknownRateException());  
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
    public ErrorDto handleException(EmptyResultDataAccessException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);
        return new ErrorDto("Could not find the entity in the database.");
    }
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({ UnknownRateException.class})
    public ErrorDto handleException(UnknownRateException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);
        return new ErrorDto("Could not find the review in the database.");
        
    } 
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({JpaObjectRetrievalFailureException.class})
    public ErrorDto handleException(JpaObjectRetrievalFailureException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);
        return new ErrorDto("Could not find the entity in the database.");
        
    }
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    public ErrorDto handleException(EntityNotFoundException e) {
        System.out.println(e.getMessage());  
        return new ErrorDto("Could not find the entity in the database.");     
    }
    
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UnknownProductException.class})
    public ErrorDto handleException(UnknownProductException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);  
        return new ErrorDto("Could not create the review since it's about an unknown product.");
    }
    
    
    
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public void handleException(MethodArgumentNotValidException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);
        
    }
    
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ErrorDto handleException(DataIntegrityViolationException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out); 
        return new ErrorDto("Could not create the review since it already exists.");
    }
     
    
}
