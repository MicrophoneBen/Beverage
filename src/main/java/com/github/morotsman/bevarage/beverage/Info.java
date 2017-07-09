package com.github.morotsman.bevarage.beverage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "info")
@XmlAccessorType(XmlAccessType.NONE)
public class Info {
    
    
    @XmlElement(name = "meddelande")
    private String message; 

    @Override
    public String toString() {
        return "Info{" + "message=" + message + '}';
    }
    
    
    
}
