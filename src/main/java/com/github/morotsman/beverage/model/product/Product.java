package com.github.morotsman.beverage.model.product;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Access;
import static javax.persistence.AccessType.PROPERTY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "product",
       indexes = {@Index(name = "my_index_name", columnList="name", unique = false),@Index(name = "my_index_producer", columnList="producer", unique = false)})
public class Product implements Serializable{
    
    @Id
    @Column(unique=true) 
    @Access(PROPERTY)
    private Long productId;
    
    private String name;
   
    private String productCategory;
    
    private Double price;
    
    private Double volume;
    
    private String nr;
    
    private String articelNumber;

    private Double pricePerLiter;

    private Date startSellDate;

    private Boolean expired;

    private String type;

    private String style;

    private String packaging;

    private String seal;
    
    private String origin;

    private String originCountry;

    private String producer;

    private String supplier;

    private String vintage;

    private String alcoholPercent;

    private String assortment;

    private String assortmentText;

    private Boolean ecologic;

    private Boolean etnic;

    private Boolean koscher;
    
    private Double averageRate;
    
    private long numberOfReviews;

    @Column(length = 1000)
    private String rawMaterialDescription;
    
    protected Product() {}

    public Product(Long productId, String name, String productCategory, Double price, Double volume, String nr, String articelNumber, 
            Double pricePerLiter, Date startSellDate, Boolean expired, String type, String style, String packaging, String seal, 
            String origin, String originCountry, String producer, String supplier, String vintage, String alcoholPercent, 
            String assortment, String assortmentText, Boolean ecologic, Boolean etnic, Boolean koscher, String rawMaterialDescription, 
            Double averageRate, long numberOfReviews) {
        this.productId = productId;
        this.name = name;
        this.productCategory = productCategory;
        this.price = price;
        this.volume = volume;
        this.nr = nr;
        this.articelNumber = articelNumber;
        this.pricePerLiter = pricePerLiter;
        this.startSellDate = startSellDate;
        this.expired = expired;
        this.type = type;
        this.style = style;
        this.packaging = packaging;
        this.seal = seal;
        this.origin = origin;
        this.originCountry = originCountry;
        this.producer = producer;
        this.supplier = supplier;
        this.vintage = vintage;
        this.alcoholPercent = alcoholPercent;
        this.assortment = assortment;
        this.assortmentText = assortmentText;
        this.ecologic = ecologic;
        this.etnic = etnic;
        this.koscher = koscher;
        this.rawMaterialDescription = rawMaterialDescription;
        this.numberOfReviews = numberOfReviews;
        this.averageRate = averageRate;
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

    public Double getVolume() {
        return volume;
    }

    public String getNr() {
        return nr;
    }

    public String getArticelNumber() {
        return articelNumber;
    }

    public Double getPricePerLiter() {
        return pricePerLiter;
    }

    public Date getStartSellDate() {
        return startSellDate;
    }

    public Boolean getExpired() {
        return expired;
    }

    public String getType() {
        return type;
    }

    public String getStyle() {
        return style;
    }

    public String getPackaging() {
        return packaging;
    }

    public String getSeal() {
        return seal;
    }

    public String getOrigin() {
        return origin;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getProducer() {
        return producer;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getVintage() {
        return vintage;
    }

    public String getAlcoholPercent() {
        return alcoholPercent;
    }

    public String getAssortment() {
        return assortment;
    }

    public String getAssortmentText() {
        return assortmentText;
    }

    public Boolean getEcologic() {
        return ecologic;
    }

    public Boolean getEtnic() {
        return etnic;
    }

    public Boolean getKoscher() {
        return koscher;
    }

    public String getRawMaterialDescription() {
        return rawMaterialDescription;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public long getNumberOfReviews() {
        return numberOfReviews;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", name=" + name + ", productCategory=" + productCategory + ", price=" + price + ", volume=" + volume + ", nr=" + nr + ", articelNumber=" + articelNumber + ", pricePerLiter=" + pricePerLiter + ", startSellDate=" + startSellDate + ", expired=" + expired + ", type=" + type + ", style=" + style + ", packaging=" + packaging + ", seal=" + seal + ", origin=" + origin + ", originCountry=" + originCountry + ", producer=" + producer + ", supplier=" + supplier + ", vintage=" + vintage + ", alcoholPercent=" + alcoholPercent + ", assortment=" + assortment + ", assortmentText=" + assortmentText + ", ecologic=" + ecologic + ", etnic=" + etnic + ", koscher=" + koscher + ", averageRate=" + averageRate + ", numberOfReviews=" + numberOfReviews + ", rawMaterialDescription=" + rawMaterialDescription + '}';
    }




    
    

    
}
