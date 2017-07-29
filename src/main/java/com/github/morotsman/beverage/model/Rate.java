package com.github.morotsman.beverage.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    uniqueConstraints=
        @UniqueConstraint(columnNames={"product_id", "user_id"})
)
public class Rate implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rateId;

    @Column(length = 1000)
    private String description;

    private Long rate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private BeverageUser bevarageUser;
    
    protected Rate() {}

    public Rate(Long rateId, String description, Long rate, Product product,BeverageUser bevarageUser) {
        this.rateId = rateId;
        this.description = description;
        this.rate = rate;
        this.product = product;
        this.bevarageUser = bevarageUser;
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

    public BeverageUser getBevarageUser() {
        return bevarageUser;
    }

    @Override
    public String toString() {
        return "Rate{" + "rateId=" + rateId + ", description=" + description + ", rate=" + rate + ", product=" + product + ", bevarageUser=" + bevarageUser + '}';
    }
    
    
    
    
}
