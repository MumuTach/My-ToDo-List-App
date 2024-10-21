package com.inn.backend.serviceImpl;

import com.inn.backend.dto.todoForm;
import com.inn.backend.model.todo;
import com.inn.backend.model.user;
import com.inn.backend.repository.todoRepository;
import com.inn.backend.service.todoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class todoServiceImpl implements todoService {

    private final todoRepository todoRepo;

    @Autowired
    public todoServiceImpl(todoRepository todoRepo) {
        this.todoRepo = todoRepo;
    }

    @Override
    public todo createToDo(user user, todoForm todo) {
        todo newTodo = new todo();
        newTodo.setTitle(todo.getTitle());
        newTodo.setDescription(todo.getDescription());
        newTodo.setStatus(false);
        newTodo.setUser(user);
        return todoRepo.save(newTodo);
    }

    @Override
    public todo updateToDo(Integer todoId, todoForm todoForm) {
        Optional<todo> existingToDo = todoRepo.findById(todoId);

        if (existingToDo.isPresent()) {
            todo todo = existingToDo.get();
            todo.setTitle(todoForm.getTitle());
            todo.setDescription(todoForm.getDescription());
            todo.setStatus(todoForm.isStatus());
            return todoRepo.save(todo);
        } else {
            throw new RuntimeException("ToDo not found with id " + todoId);
        }
    }

    @Override
    public void deleteToDo(Integer id) {
        todoRepo.deleteById(id);
    }

    @Override
    public List<todo> findByUserId(Integer userId) {
        return todoRepo.findTodoByUserId(userId);
    }

    @Override
    public List<todo> findByUserIdAndStatus(Integer userId, Boolean status) {
        return todoRepo.findByUserIdAndStatus(userId, status);
    }
}
