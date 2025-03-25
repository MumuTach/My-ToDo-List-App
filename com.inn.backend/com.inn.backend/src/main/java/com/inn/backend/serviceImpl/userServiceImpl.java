package com.inn.backend.serviceImpl;

import com.inn.backend.model.user;
import com.inn.backend.repository.userRepository;
import com.inn.backend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userServiceImpl implements userService {

    private final userRepository iUser;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public userServiceImpl(userRepository iUser, PasswordEncoder passwordEncoder) {
        this.iUser = iUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public user getUserByEmail(String email) {
        return iUser.findByEmail(email);
    }

    @Override
    public user getUserById(int id) { return iUser.findUserById(id);}

    @Override
    public user registerUser(user user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return iUser.save(user);
    }
}
