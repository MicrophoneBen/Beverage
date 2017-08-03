package com.github.morotsman.beverage.model.user;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class BeverageUser implements UserDetails, Serializable{
    
    @Id
    private String userName;
    private String password;
    private Long age;

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }
    

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public Long getAge() {
        return age;
    }
    
    

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public BeverageUser(String userName, String password, Long age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }
    
    protected BeverageUser(){
    }

    @Override
    public String toString() {
        return "BeverageUser{" + "userName=" + userName + ", password=" + password + ", age=" + age + '}';
    }

    
    
      
    
}
