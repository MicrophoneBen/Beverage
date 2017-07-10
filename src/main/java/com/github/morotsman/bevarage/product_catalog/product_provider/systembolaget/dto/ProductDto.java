package com.github.morotsman.bevarage.product_catalog.product_provider.systembolaget.dto;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "artikel")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductDto {

    @XmlElement
    private String nr;

    @XmlElement(name = "Artikelid")
    private String articelNumber;

    @XmlElement(name = "Varnummer")
    private Long productId;

    @XmlElement(name = "Namn")
    private String name1;

    @XmlElement(name = "Namn2")
    private String name2;

    @XmlElement(name = "Prisinklmoms")
    private Double price;

    @XmlElement(name = "Volymiml")
    private Double volume;

    @XmlElement(name = "PrisPerLiter")
    private Double pricePerLiter;

    @XmlElement(name = "Saljstart")
    //@XmlSchemaType(name = "date")
    private Date startSellDate;

    @XmlElement(name = "Utgått")
    private Boolean expired;

    @XmlElement(name = "Varugrupp")
    private String productCategory;

    @XmlElement(name = "Typ")
    private String type;

    @XmlElement(name = "Stil")
    private String style;

    @XmlElement(name = "Forpackning")
    private String packaging;

    @XmlElement(name = "Forslutning")
    private String seal;

    @XmlElement(name = "Ursprung")
    private String origin;

    @XmlElement(name = "Ursprunglandnamn")
    private String originCountry;

    @XmlElement(name = "Producent")
    private String producer;

    @XmlElement(name = "Leverantor")
    private String supplier;

    @XmlElement(name = "Argang")
    private String vintage;

    @XmlElement(name = "Alkoholhalt")
    private String alcoholPercent;

    @XmlElement(name = "Sortiment")
    private String assortment;

    @XmlElement(name = "SortimentText")
    private String assortmentText;

    @XmlElement(name = "Ekologisk")
    private Boolean ecologic;

    @XmlElement(name = "Etiskt")
    private Boolean etnic;

    @XmlElement(name = "Koscher")
    private Boolean koscher;

    @XmlElement(name = "RavarorBeskrivning")
    private String rawMaterialDescription;
    /*
     <artikel><RavarorBeskrivning>Säd.</RavarorBeskrivning></artikel>
     */

    @Override
    public String toString() {
        return "Product{" + "nr=" + nr + ", articelNumber=" + articelNumber + ", productId=" + productId + ", name1=" + name1 + ", name2=" + name2 + ", price=" + price + ", volume=" + volume + ", pricePerLiter=" + pricePerLiter + ", startSellDate=" + startSellDate + ", expired=" + expired + ", productCategory=" + productCategory + ", type=" + type + ", style=" + style + ", packaging=" + packaging + ", seal=" + seal + ", origin=" + origin + ", originCountry=" + originCountry + ", producer=" + producer + ", supplier=" + supplier + ", vintage=" + vintage + ", alcoholPercent=" + alcoholPercent + ", assortment=" + assortment + ", assortmentText=" + assortmentText + ", ecologic=" + ecologic + ", etnic=" + etnic + ", koscher=" + koscher + ", rawMaterialDescription=" + rawMaterialDescription + '}';
    }

    public String getNr() {
        return nr;
    }

    public String getArticelNumber() {
        return articelNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public Double getPrice() {
        return price;
    }

    public Double getVolume() {
        return volume;
    }

    public Double getPricePerLiter() {
        return pricePerLiter;
    }

    public Date getStartSellDate() {
        return startSellDate;
    }

    public Boolean isExpired() {
        return expired;
    }

    public String getProductCategory() {
        return productCategory;
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

    public Boolean isEcologic() {
        return ecologic;
    }

    public Boolean isEtnic() {
        return etnic;
    }

    public Boolean isKoscher() {
        return koscher;
    }

    public String getRawMaterialDescription() {
        return rawMaterialDescription;
    }
    
    


}
