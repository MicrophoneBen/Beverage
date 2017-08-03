package com.github.morotsman.beverage.model.user;

import com.github.morotsman.beverage.model.user.BeverageUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BeverageUserRepository extends JpaRepository<BeverageUser,String> {
    
    
}
