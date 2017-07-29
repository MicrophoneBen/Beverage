package com.github.morotsman.beverage.model;

import java.util.stream.Stream;
import org.springframework.data.repository.CrudRepository;


public interface RateRepository extends CrudRepository<Rate, Long> {
    
    Stream<Rate> findByBevarageUserOrderByUpdatedDesc(BeverageUser user);  
    
    void deleteByRateIdAndBevarageUser(long rateId, BeverageUser user);    
    
}
