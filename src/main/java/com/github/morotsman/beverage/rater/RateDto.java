package com.github.morotsman.beverage.rater;

import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class RateDto {
    
    private Long rateId;

    private String description;
    
    private String name;
    
    private String producer;

    @NotNull(message = "The review must contain a grade.")
    @Min(value=0, message="The value of the grade must be between at least 0 and at most 10.")
    @Max(value=10, message="The value of the grade must be between at least 0 and at most 10.")
    private Long rate;

    @NotNull(message="A review must be about a product.")
    private Long productId;
    

    protected RateDto(){}
    
    public RateDto(Long rateId, String description, Long rate, Long productId, String name, String producer) {
        this.rateId = rateId;
        this.description = description;
        this.rate = rate;
        this.productId = productId;
        this.name = name;
        this.producer = producer;
    }

    public Long getRateId() {
        return rateId;
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

    @Override
    public String toString() {
        return "RateDto{" + "rateId=" + rateId + ", description=" + description + ", rate=" + rate + ", productId=" + productId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.rateId);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.rate);
        hash = 59 * hash + Objects.hashCode(this.productId);
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
        final RateDto other = (RateDto) obj;
        if (!Objects.equals(this.rateId, other.rateId)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.rate, other.rate)) {
            return false;
        }
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
}
