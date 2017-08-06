package com.github.morotsman.beverage.user.service;

import com.github.morotsman.beverage.model.user.BeverageUser;
import com.github.morotsman.beverage.model.user.BeverageUserRepository;
import com.github.morotsman.beverage.user.BeverageUserDto;
import com.github.morotsman.beverage.user.UserService;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final BeverageUserRepository userRepository;
    final StandardPasswordEncoder encoder;
    final EntityManager entityManager;

    public UserServiceImpl(BeverageUserRepository userRepository, StandardPasswordEncoder encoder, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public BeverageUserDto createUser(BeverageUserDto user) {
        BeverageUser checkForUser = userRepository.findOne(user.getUsername());
        if(checkForUser != null) throw new EntityExistsException("There already exists a user with the same username: " + user.getUsername() +".");
        
        
        entityManager.persist(new BeverageUser(user.getUsername(), encoder.encode(user.getPassword()), user.getAge()));
        entityManager.flush();
        return new BeverageUserDto(null, user.getUsername(), user.getAge());
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
