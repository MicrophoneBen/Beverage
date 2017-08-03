package com.github.morotsman.beverage.product_catalog.service.product_provider.systembolaget.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "artiklar")
@XmlAccessorType(XmlAccessType.NONE)  
public class ProductCatalogeDto {
    
    //
    @XmlElement(name = "skapad-tid")
    private String time;
    
    @XmlElement(name = "info")  
    private InfoDto info;  
    
    @XmlElement(name = "artikel")
    private List<ProductDto> products = new ArrayList<ProductDto>();

    public String getTime() {
        return time;
    }

    public InfoDto getInfo() {
        return info;  
    }

    public List<ProductDto> getProducts() {
        return products;
    }
    
    

    @Override
    public String toString() {
        return "ProductCataloge{" + "time=" + time + ", info=" + info + ", products=" + products + '}';
    }

   
    
    
}
