package com.github.morotsman.beverage.model;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface RateRepository extends PagingAndSortingRepository<Rate, Long> {
    
    
    Stream<Rate> findByBevarageUserOrderByUpdatedDesc(BeverageUser user,final Pageable pageable); 
    
    Stream<Rate> findDistinctByBevarageUserAndNameIgnoreCaseContainingOrderByUpdatedDesc(BeverageUser user,final String name, final Pageable pageable); 
    
    
    Stream<Rate> findDistinctByBevarageUserAndNameIgnoreCaseContainingOrBevarageUserAndProducerIgnoreCaseContainingOrderByUpdatedDesc(BeverageUser user1,final String name, BeverageUser user2, final String producer,final Pageable pageable); 
    
    
    
    void deleteByRateIdAndBevarageUser(long rateId, BeverageUser user);  
    
    Optional<Rate> findOne(long rateId);
    
    
    
}
