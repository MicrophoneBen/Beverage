package com.github.morotsman.bevarage.rater;

import com.github.morotsman.bevarage.product_catalog.model.Rate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/rate")
public class RateController {
    
    private final RateService rateService;
    
    public RateController(final RateService rateService) {
        this.rateService = rateService;
    }
    
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Rate> getRates() {
        return rateService.getRates();
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RateDto createRate(@RequestBody RateDto rate) {
        return rateService.createRate(rate);
    }
    
    @RequestMapping(value = "/{rateId}", method = RequestMethod.GET)
    public Rate getRate(@PathVariable long rateId) {
        return rateService.getRate(rateId);
    }
    
    @RequestMapping(value = "/{rateId}", method = RequestMethod.PUT)
    public RateDto updateRate(@PathVariable long rateId,@RequestBody RateDto rate) {
        return rateService.updateRate(rate);
    }
    
    @RequestMapping(value = "/{rateId}", method = RequestMethod.DELETE)
    public void deleteRate(@PathVariable long rateId) {
        rateService.deleteRate(rateId);  
    }
}
