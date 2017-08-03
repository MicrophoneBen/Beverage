package com.github.morotsman.beverage.model.rate;

import com.github.morotsman.beverage.model.product.Product;
import com.github.morotsman.beverage.model.user.BeverageUser;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    
    private String name;
    
    private String producer;

    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private BeverageUser bevarageUser;
    
    private Long updated;
    
    protected Rate() {}

    public Rate(Long rateId, String description, Long rate, Product product,BeverageUser bevarageUser, Long updated, String name, String producer) {
        this.rateId = rateId;
        this.description = description;
        this.rate = rate;
        this.product = product;
        this.bevarageUser = bevarageUser;
        this.updated = updated;
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

    public Product getProduct() {
        return product;
    }

    public BeverageUser getBevarageUser() {
        return bevarageUser;
    }

    public Long getUpdated() {
        return updated;
    }

    public String getName() {
        return name;
    }

    public String getProducer() {
        return producer;
    }
    
    



    
    
    
    
}
