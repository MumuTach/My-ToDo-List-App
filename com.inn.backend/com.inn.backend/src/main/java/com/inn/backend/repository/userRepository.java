package com.inn.backend.repository;

import com.inn.backend.model.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user,Integer> {
    user findByEmail(String username);
    user findUserById(int id);
}
