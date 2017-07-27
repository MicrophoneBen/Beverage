package com.github.morotsman.beverage.user.service;

import com.github.morotsman.beverage.model.BeverageUser;
import com.github.morotsman.beverage.model.BeverageUserRepository;
import com.github.morotsman.beverage.user.BeverageUserDto;
import com.github.morotsman.beverage.user.UserService;
import javax.persistence.EntityManager;
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
        entityManager.persist(new BeverageUser(user.getUsername(), encoder.encode(user.getPassword()), user.getAge()));
        entityManager.flush();
        return new BeverageUserDto(null, user.getUsername(), user.getAge());
    }

    @Override
    @Transactional
    public BeverageUserDto getUser(String username) {
        BeverageUser user = userRepository.findOne(username);
        return new BeverageUserDto(null, user.getUsername(), user.getAge());
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
