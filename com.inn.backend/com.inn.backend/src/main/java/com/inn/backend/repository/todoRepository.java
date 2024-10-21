package com.inn.backend.repository;

import com.inn.backend.model.todo;
import com.inn.backend.model.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface todoRepository extends JpaRepository<todo,Integer> {
    List<todo> findTodoByUserId(Integer userId);
    List<todo> findByUserIdAndStatus(Integer userId, Boolean status);
}
