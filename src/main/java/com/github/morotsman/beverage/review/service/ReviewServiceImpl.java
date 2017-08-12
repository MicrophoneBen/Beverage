package com.github.morotsman.beverage.review.service;

import com.github.morotsman.beverage.model.user.BeverageUser;
import com.github.morotsman.beverage.model.product.ProductRepository;
import com.github.morotsman.beverage.model.product.Product;
import com.github.morotsman.beverage.model.review.Review;
import com.github.morotsman.beverage.review.ReviewDto;
import com.github.morotsman.beverage.model.review.ReviewRepository;
import com.github.morotsman.beverage.review.ReviewService;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    
    private final ReviewRepository reviewRepository;
    
    private final ProductRepository productRepository;
    
    private final EntityManager entityManager;
    
    private final int pageSize;
    
    public ReviewServiceImpl(final ReviewRepository reviewRepository, final ProductRepository productRepository, 
            final EntityManager entityManager,
            final @Value("${review.page_size}") int pageSize) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.entityManager= entityManager;
        this.pageSize= pageSize;
    }
    
    private Review fromDto(final ReviewDto reviewDto, final Product product, final BeverageUser beverageUser) {
        return new Review(reviewDto.getReviewId(), reviewDto.getDescription(), reviewDto.getRate(), product, beverageUser,System.currentTimeMillis(), product.getName(), product.getProducer(), beverageUser.getUsername());
    }
   
    
    private ReviewDto toDto(final Review review) {
        return new ReviewDto(review.getReviewId(), review.getDescription(), review.getRate(), review.getProduct().getProductId(), review.getName(), review.getProducer(), review.getReviewer());
    }

    @Transactional
    @Override 
    public Optional<ReviewDto> createReview(final String username, final ReviewDto review) {
        return productRepository
                .findOne(review.getProductId().longValue())
                .map(product -> {
                    final BeverageUser user = entityManager.getReference(BeverageUser.class, username);
                    return fromDto(review,product,user);
                })
                .map(reviewRepository::save)
                .map(this::toDto);
    }

    @Transactional
    @Override
    public Optional<ReviewDto> getReview(final String username, long reviewId) {          
        return getReviewIfOwnedByUser(username, reviewId)
                .map(this::toDto);  
    }
    
    private Optional<Review> getReviewIfOwnedByUser(final String username, long reviewId) {
        return reviewRepository.findOne(reviewId)
                .filter(rate -> rate.getBevarageUser().getUsername().equals(username));
    }

    @Transactional
    @Override
    public Optional<ReviewDto> updateReview(final String username, ReviewDto reviewDto) {
        return getReviewIfOwnedByUser(username, reviewDto.getReviewId())
                .map((Review rate) -> {
                    return fromDto(reviewDto, rate.getProduct(), rate.getBevarageUser());
                })
                .map(reviewRepository::save)
                .map(this::toDto);
    }

    @Transactional
    @Override
    public Optional<Boolean> deleteReview(final String username, long reviewId) {
        return getReviewIfOwnedByUser(username, reviewId)
                .map(rate -> {
                    reviewRepository.deleteByReviewIdAndBevarageUser(reviewId, rate.getBevarageUser());
                    return true;
                });
    }  
    
    @Transactional
    @Override
    public Iterable<ReviewDto> getReviews(String username, String query, int page) {
        //TODO add to application.properties
        PageRequest pageRequest = new PageRequest(page, pageSize,Sort.DEFAULT_DIRECTION, new String[]{"updated"});
        BeverageUser user = entityManager.getReference(BeverageUser.class, username);
        return reviewRepository.findDistinctByBevarageUserAndNameIgnoreCaseContainingOrBevarageUserAndProducerIgnoreCaseContainingOrderByUpdatedDesc(user,query,user,query,pageRequest)
                .stream() 
                .map(r -> new ReviewDto(r.getReviewId(), r.getDescription(), r.getRate(), r.getProduct().getProductId(), r.getName(), r.getProducer()))  
                .collect(Collectors.toList());
    }    
    
    @Transactional
    @Override
    public void deleteAllReviews() {
        reviewRepository.deleteAll();
    }

    @Transactional
    @Override
    public Iterable<ReviewDto> getReviewsForProduct(Long productId, int page) {
        //TODO add page size to aplication.properties
        PageRequest pageRequest = new PageRequest(page, 5,Sort.DEFAULT_DIRECTION, new String[]{"updated"});
        Product product = entityManager.getReference(Product.class, productId);
        return reviewRepository.findByProduct(product, pageRequest).stream().map(this::toDto).collect(Collectors.toList());
    }
   
}
