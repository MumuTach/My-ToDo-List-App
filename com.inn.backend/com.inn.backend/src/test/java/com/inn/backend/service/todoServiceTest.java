package com.inn.backend.service;

import com.inn.backend.dto.todoForm;
import com.inn.backend.model.todo;
import com.inn.backend.model.user;
import com.inn.backend.repository.todoRepository;
import com.inn.backend.serviceImpl.todoServiceImpl;
import com.inn.backend.utils.testData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class todoServiceTest {

    @Mock
    private todoRepository todoRepo;

    @InjectMocks
    private todoServiceImpl underTest;

    @Test
    void testCreateToDo() {
        user testUser = testData.Existing_User;
        todoForm form = new todoForm();
        form.setTitle("Nouvelle tâche");
        form.setDescription("Description de la tâche");

        todo newTodo = new todo();
        newTodo.setTitle(form.getTitle());
        newTodo.setDescription(form.getDescription());
        newTodo.setStatus(false);
        newTodo.setUser(testUser);

        when(todoRepo.save(any(todo.class))).thenReturn(newTodo);

        todo createdTodo = underTest.createToDo(testUser, form);

        assertNotNull(createdTodo);
        assertEquals(form.getTitle(), createdTodo.getTitle());
        assertEquals(form.getDescription(), createdTodo.getDescription());
        verify(todoRepo, times(1)).save(any(todo.class));
    }

    @Test
    void testUpdateToDo() {
        todo existingTodo = testData.todos.get(0);
        todoForm form = new todoForm();
        form.setTitle("Mise à jour du titre");
        form.setDescription("Nouvelle description");
        form.setStatus(true);

        when(todoRepo.findById(existingTodo.getId())).thenReturn(Optional.of(existingTodo));
        when(todoRepo.save(any(todo.class))).thenReturn(existingTodo);

        todo updatedTodo = underTest.updateToDo(existingTodo.getId(), form);

        assertNotNull(updatedTodo);
        assertEquals(form.getTitle(), updatedTodo.getTitle());
        assertEquals(form.getDescription(), updatedTodo.getDescription());
        assertTrue(updatedTodo.isStatus());
        verify(todoRepo, times(1)).save(existingTodo);
    }

    @Test
    void testDeleteToDo() {
        doNothing().when(todoRepo).deleteById(1);
        underTest.deleteToDo(1);
        verify(todoRepo, times(1)).deleteById(1);
    }

    @Test
    void testFindToDoByUserId() {
        when(todoRepo.findTodoByUserId(testData.Existing_User.getId()))
                .thenReturn(testData.todos);
        List<todo> todos = underTest.findByUserId(testData.Existing_User.getId());
        assertNotNull(todos);
        assertEquals(2, todos.size());
    }

    @Test
    void testFindByUserIdAndStatus() {
        when(todoRepo.findByUserIdAndStatus(testData.Existing_User.getId(), false))
                .thenReturn(List.of(testData.todos.get(0)));

        List<todo> todos = underTest.findByUserIdAndStatus(testData.Existing_User.getId(), false);

        System.out.println("Statut de la tâche récupérée: " + todos.get(0).isStatus());
        System.out.println("Statut dans testData: " + testData.todos.get(0).isStatus());
        System.out.println("Mock retourne: " + todoRepo.findByUserIdAndStatus(testData.Existing_User.getId(), false));
        assertNotNull(todos);
        assertEquals(1, todos.size());
        assertFalse(todos.get(0).isStatus());
    }
}
