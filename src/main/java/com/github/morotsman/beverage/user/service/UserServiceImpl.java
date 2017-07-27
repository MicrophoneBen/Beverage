package com.github.morotsman.beverage.user.service;

import com.github.morotsman.beverage.model.BeverageUser;
import com.github.morotsman.beverage.model.BeverageUserRepository;
import com.github.morotsman.beverage.user.BeverageUserDto;
import com.github.morotsman.beverage.user.UserService;
import javax.transaction.Transactional;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    final BeverageUserRepository userRepository;
    final StandardPasswordEncoder encoder;

    public UserServiceImpl(BeverageUserRepository userRepository, StandardPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    


    @Override
    @Transactional
    public BeverageUserDto createUser(BeverageUserDto user) {
        userRepository.save(new BeverageUser(user.getUsername(), encoder.encode(user.getPassword())));
        return new BeverageUserDto(null, user.getUsername());
    }

    @Override
    @Transactional
    public BeverageUserDto getUser(String username) {
        BeverageUser user = userRepository.findOne(username);
        return new BeverageUserDto(null, user.getUsername());
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }


    
}
