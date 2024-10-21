package com.inn.backend.controller;

import com.inn.backend.dto.todoForm;
import com.inn.backend.model.todo;
import com.inn.backend.model.user;
import com.inn.backend.service.todoService;
import com.inn.backend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class todoController {

    @Autowired
    private todoService toDoService;

    @Autowired
    private userService userService;

    @GetMapping("/{userId}")
    public List<todo> getTodosByUser(@PathVariable Integer userId) {
        return toDoService.findByUserId(userId);
    }

    @GetMapping("/{userId}/{status}")
    public List<todo> getTodosByUserAndStatus(@PathVariable Integer userId, @PathVariable Boolean status) {
        return toDoService.findByUserIdAndStatus(userId, status);
    }

    @PostMapping("add/{userId}")
    public ResponseEntity<todo> addToDo(@PathVariable Integer userId, @RequestBody todoForm todoForm) {
        user user = userService.getUserBId(userId);
        todo newToDo = toDoService.createToDo(user, todoForm);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(newToDo);
    }

    // Endpoint pour supprimer un ToDo
    @DeleteMapping("/{todoId}")
    public void deleteToDo(@PathVariable Integer todoId) {
        toDoService.deleteToDo(todoId);
    }

    @PutMapping("/{todoId}")
    public todo updateToDo(@PathVariable Integer todoId, @RequestBody todoForm todoForm) {
        return toDoService.updateToDo(todoId, todoForm);
    }
}
