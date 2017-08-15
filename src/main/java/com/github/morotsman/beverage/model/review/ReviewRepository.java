package com.github.morotsman.beverage.model.review;

import com.github.morotsman.beverage.model.product.Product;
import com.github.morotsman.beverage.model.user.BeverageUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {
    
    
    List<Review> findByBevarageUserOrderByUpdatedDesc(BeverageUser user,final Pageable pageable); 
    
    List<Review> findDistinctByBevarageUserAndNameIgnoreCaseContainingOrderByUpdatedDesc(BeverageUser user,final String name, final Pageable pageable); 
    
    
    List<Review> findDistinctByBevarageUserAndNameIgnoreCaseContainingOrBevarageUserAndProducerIgnoreCaseContainingOrderByUpdatedDesc(BeverageUser user1,final String name, BeverageUser user2, final String producer,final Pageable pageable); 
    
    
    void deleteByReviewIdAndBevarageUser(long reviewId, BeverageUser user);  
    
    Optional<Review> findOne(long rateId);
    
    List<Review> findByProduct(Product product, Pageable pageable);
    
    List<Review> findByProduct(Product product);
    
    
    
}
