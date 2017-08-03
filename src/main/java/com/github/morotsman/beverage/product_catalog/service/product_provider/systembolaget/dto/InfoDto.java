package com.github.morotsman.beverage.product_catalog.service.product_provider.systembolaget.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "info")
@XmlAccessorType(XmlAccessType.NONE)
public class InfoDto {
    
    
    @XmlElement(name = "meddelande")
    private String message; 

    @Override
    public String toString() {
        return "Info{" + "message=" + message + '}';
    }
    
    
    
}
