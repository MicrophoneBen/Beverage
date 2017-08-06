package com.github.morotsman.beverage.review;

import com.github.morotsman.beverage.common.ErrorDto;
import com.github.morotsman.beverage.model.exceptions.UnknownReviewException;
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
@RequestMapping("v1/review")
public class ReviewController {
    
    private final ReviewService rateService;
    
    public ReviewController(final ReviewService rateService) {
        this.rateService = rateService;
    }
    
    @GetMapping(value = "")
    public Iterable<ReviewDto> getReviews(Principal principal, final @RequestParam(required=false) String query, final @RequestParam(required=false) Integer page) {
        return rateService.getReviews(principal.getName(), query==null?"":query, page==null?0:page);
    }
     
    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ReviewDto createReview(@RequestBody @Valid ReviewDto review, Principal principal) {
        return rateService.createReview(principal.getName(),review).orElseThrow(() -> new UnknownProductException());
    }
    
    
    @GetMapping(value = "/{reviewId}")
    public ReviewDto getReview(@PathVariable long reviewId, Principal principal) {
        return rateService
                .getReview(principal.getName(),reviewId)
                .orElseThrow(() -> new UnknownReviewException());
    }
    
    @PutMapping(value = "/{reviewId}")
    public ReviewDto updateReview(@PathVariable long reviewId,@RequestBody @Valid ReviewDto review, Principal principal) {
        return rateService
                .updateReview(principal.getName(),review)
                .orElseThrow(() -> new UnknownReviewException());
    }
    
    @DeleteMapping(value = "/{reviewId}")
    public void deleteReview(@PathVariable long reviewId, Principal principal) {
        rateService
                .deleteReview(principal.getName(),reviewId)
                .orElseThrow(() -> new UnknownReviewException());  
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
    @ExceptionHandler({ UnknownReviewException.class})
    public ErrorDto handleException(UnknownReviewException e) {
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
