package com.github.morotsman.beverage.rater;


public class RateDto {
    
    private Long rateId;

    private String description;

    private Long rate;

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
    
}
