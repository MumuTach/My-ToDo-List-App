package com.inn.backend.service;

import com.inn.backend.dto.todoForm;
import com.inn.backend.model.todo;
import com.inn.backend.model.user;

import java.util.List;

public interface todoService {
    todo createToDo(user user, todoForm todo);
    todo updateToDo(Integer id, todoForm todo);
    void deleteToDo(Integer id);
    List<todo> findByUserId(Integer userId);
    List<todo> findByUserIdAndStatus(Integer userId, Boolean status);
}
