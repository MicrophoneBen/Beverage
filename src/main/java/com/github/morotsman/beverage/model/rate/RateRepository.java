package com.github.morotsman.beverage.model.rate;

import com.github.morotsman.beverage.model.user.BeverageUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface RateRepository extends PagingAndSortingRepository<Rate, Long> {
    
    
    List<Rate> findByBevarageUserOrderByUpdatedDesc(BeverageUser user,final Pageable pageable); 
    
    List<Rate> findDistinctByBevarageUserAndNameIgnoreCaseContainingOrderByUpdatedDesc(BeverageUser user,final String name, final Pageable pageable); 
    
    
    List<Rate> findDistinctByBevarageUserAndNameIgnoreCaseContainingOrBevarageUserAndProducerIgnoreCaseContainingOrderByUpdatedDesc(BeverageUser user1,final String name, BeverageUser user2, final String producer,final Pageable pageable); 
    
    
    
    void deleteByRateIdAndBevarageUser(long rateId, BeverageUser user);  
    
    Optional<Rate> findOne(long rateId);
    
    
    
}
