package com.github.morotsman.bevarage.product_catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
    
    @Id
    @Column(unique=true)
    private Long productId;
    
    private String name;
   
    private String productCategory;
    
    private Double price;
    
    protected Product() {}

    public Product(Long productId, String name, String productCategory, Double price) {
        this.productId = productId;
        this.name = name;
        this.productCategory = productCategory;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", name=" + name + ", productCategory=" + productCategory + ", price=" + price + '}';
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public Double getPrice() {
        return price;
    }


    

    
    
    
    
    
    
    
}
