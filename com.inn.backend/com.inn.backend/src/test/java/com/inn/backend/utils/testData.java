package com.inn.backend.utils;

import com.inn.backend.model.todo;
import com.inn.backend.model.user;

import java.util.ArrayList;
import java.util.List;

public class testData {

    public static final user Existing_User = new user();
    public static final user New_User = new user();
    public static final List<todo> todos = new ArrayList<>();

    static {
        //utilisateur existant
        Existing_User.setId(1);
        Existing_User.setName("manuella");
        Existing_User.setEmail("manuella@gmail.com");
        Existing_User.setPassword("$2a$10$QWERTYUIOPLKJHGFDSA");

        // Nouvel utilisateur
        New_User.setName("Alice Smith");
        New_User.setEmail("alice.smith@example.com");
        New_User.setPassword("password123");

        //création des todos associé à un existing_user
        todo todo1 = new todo();
        todo1.setId(1);
        todo1.setTitle("Acheter du lait");
        todo1.setDescription("Aller au supermarché pour acheter du lait");
        todo1.setStatus(false);
        todo1.setUser(Existing_User);

        todo todo2 = new todo();
        todo2.setId(2);
        todo2.setTitle("Faire du sport");
        todo2.setDescription("Faire du sport 30min");
        todo2.setStatus(false);
        todo2.setUser(Existing_User);

        todos.add(todo1);
        todos.add(todo2);
    }

}
