package com.github.morotsman.bevarage.beverage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "artikel")
@XmlAccessorType(XmlAccessType.NONE)
public class Product {
    
    @XmlElement
    private String nr;
    
    @XmlElement(name = "Artikelid")
    private String articelNumber;
    
    
    @XmlElement(name = "Varnummer")
    private String productId;
    
    @XmlElement(name = "Namn")
    private String name1;
    
    @XmlElement(name = "Namn2")
    private String name2;

    @Override
    public String toString() {
        return "Product{" + "nr=" + nr + ", articelNumber=" + articelNumber + ", productId=" + productId + ", name1=" + name1 + ", name2=" + name2 + '}';
    }
    
    
    
    


    
    
    
}
