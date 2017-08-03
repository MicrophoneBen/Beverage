package com.github.morotsman.beverage.model;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BeverageUserRepository extends JpaRepository<BeverageUser,String> {
    
    
}
