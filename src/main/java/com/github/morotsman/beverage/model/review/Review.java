package com.github.morotsman.beverage.model.review;

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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
    uniqueConstraints=
        @UniqueConstraint(columnNames={"product_id", "user_id"})
)
public class Review implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Long rate;
    
    @Column(nullable = false)
    private String name;
    
    private String producer;

    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BeverageUser bevarageUser;
    
    private Long updated;
    
    protected Review() {}

    public Review(Long reviewId, String description, Long rate, Product product,BeverageUser bevarageUser, Long updated, String name, String producer) {
        this.reviewId = reviewId;
        this.description = description;
        this.rate = rate;
        this.product = product;
        this.bevarageUser = bevarageUser;
        this.updated = updated;
        this.name = name;
        this.producer = producer;
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
