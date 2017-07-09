package com.github.morotsman.bevarage.beverage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "artiklar")
@XmlAccessorType(XmlAccessType.NONE)
public class Products {
    
    //
    @XmlElement(name = "skapad-tid")
    private String time;
    
    @XmlElement(name = "info")
    private Info info;  
    
    @XmlElement(name = "artikel")
    private List<Product> employees = new ArrayList<Product>();

    @Override
    public String toString() {
        return "Products{" + "time=" + time + ", info=" + info + ", employees=" + employees + '}';
    }

   
    
    
}
