package com.inn.backend.service;

import com.inn.backend.model.user;

import java.util.Optional;

public interface userService {
    user getUserByEmail(String email);
    user getUserBId(int id);
    user registerUser(user user);
}