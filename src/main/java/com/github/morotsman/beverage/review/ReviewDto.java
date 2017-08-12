package com.github.morotsman.beverage.review;

import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class ReviewDto {
    
    private Long reviewId;

    private String description;
    
    private String name;
    
    private String producer;

    @NotNull(message = "The review must contain a grade.")
    @Min(value=0, message="The value of the grade must be between at least 0 and at most 10.")
    @Max(value=10, message="The value of the grade must be between at least 0 and at most 10.")
    private Long rate;

    @NotNull(message="A review must be about a product.")
    private Long productId;
    
    private String reviewer;
    

    protected ReviewDto(){}
    
    public ReviewDto(Long reviewId, String description, Long rate, Long productId, String name, String producer, String reviewer) {
        this.reviewId = reviewId;
        this.description = description;
        this.rate = rate;
        this.productId = productId;
        this.name = name;
        this.producer = producer;
        this.reviewer = reviewer;
    }
    
    public ReviewDto(Long reviewId, String description, Long rate, Long productId, String name, String producer) {
        this(reviewId, description, rate, productId, name, producer, null);
    }

    public Long getReviewId() {
        return reviewId;
    }

    public String getDescription() {
        return description;
    }

    public Long getRate() {
        return rate;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getProducer() {
        return producer;
    }

    public String getReviewer() {
        return reviewer;
    }

    @Override
    public String toString() {
        return "ReviewDto{" + "reviewId=" + reviewId + ", description=" + description + ", name=" + name + ", producer=" + producer + ", rate=" + rate + ", productId=" + productId + ", reviewer=" + reviewer + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.reviewId);
        hash = 17 * hash + Objects.hashCode(this.description);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.producer);
        hash = 17 * hash + Objects.hashCode(this.rate);
        hash = 17 * hash + Objects.hashCode(this.productId);
        hash = 17 * hash + Objects.hashCode(this.reviewer);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReviewDto other = (ReviewDto) obj;
        if (!Objects.equals(this.reviewId, other.reviewId)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.producer, other.producer)) {
            return false;
        }
        if (!Objects.equals(this.rate, other.rate)) {
            return false;
        }
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        if (!Objects.equals(this.reviewer, other.reviewer)) {
            return false;
        }
        return true;
    }

    
    
    
    
    
    
    
}
