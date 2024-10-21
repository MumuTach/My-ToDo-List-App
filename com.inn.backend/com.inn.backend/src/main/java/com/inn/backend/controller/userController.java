package com.inn.backend.controller;

import com.inn.backend.model.user;
import com.inn.backend.service.userService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    userService UserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/email/{email}")
    public ResponseEntity<user> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(UserService.getUserByEmail(email));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<user> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(UserService.getUserBId(id));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        user foundUser = UserService.getUserByEmail(loginData.get("email"));
        if(foundUser != null && passwordEncoder.matches(loginData.get("password"), foundUser.getPassword())) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Login successful", "user", Map.of(
                    "id", foundUser.getId()
            )));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Invalid email or password"));

        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody user user) {
        Map<String, Object> response = new HashMap<>();

        user foundUser = UserService.getUserByEmail(user.getEmail());
        if(foundUser != null) {
            response.put("success", false);
            response.put("message", "Email already in use");
            return ResponseEntity.status(409).body(response);
        } else {
            user newUser = UserService.registerUser(user);
            response.put("success", true);
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON) 
                .body("{\"message\":\"Logged out successfully\"}");
    }
}