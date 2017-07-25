package com.github.morotsman.beverage.rater;

import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class RateDto {
    
    private Long rateId;

    private String description;

    @NotNull
    @Min(0)
    @Max(10)
    private Long rate;

    @NotNull
    private Long productId;
    

    protected RateDto(){}
    
    public RateDto(Long rateId, String description, Long rate, Long productId) {
        this.rateId = rateId;
        this.description = description;
        this.rate = rate;
        this.productId = productId;
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

    public long getProductId() {
        return productId;
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
