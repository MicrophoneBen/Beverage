package com.github.morotsman.bevarage.product_catalog.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Rate implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rateId;

    @Column(length = 1000)
    private String description;

    private Long rate;

    @ManyToOne
    private Product product;
    
    protected Rate() {}

    public Rate(Long rateId, String description, Long rate, Product product) {
        this.rateId = rateId;
        this.description = description;
        this.rate = rate;
        this.product = product;
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

    public Product getProduct() {
        return product;
    }
    
    
}
