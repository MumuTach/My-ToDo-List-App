package com.inn.backend.service;

import com.inn.backend.model.user;
import com.inn.backend.repository.userRepository;
import com.inn.backend.serviceImpl.userServiceImpl;
import com.inn.backend.utils.testData;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class userServiceTest implements WithAssertions {

    @Mock
    private userRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private userServiceImpl underTest;


    @Test
    void givenService_whenFindingUserById_thenFindUser() {
        System.out.println("ID attendu : " + testData.Existing_User.getId());

        when(userRepo.findUserById(testData.Existing_User.getId())).thenReturn(testData.Existing_User);
        var foundUser = underTest.getUserById(testData.Existing_User.getId());

        System.out.println("Mock userRepo.findById(1) : " + userRepo.findById(1));
        System.out.println("Résultat de getUserById : " + foundUser);

        assertNotNull(foundUser);
        assertEquals(testData.Existing_User.getId(), foundUser.getId());
    }


    @Test
    void givenService_whenFindingUserByEmail_thenFindUser() {
        when(userRepo.findByEmail(testData.Existing_User.getEmail())).thenReturn(testData.Existing_User);
        var foundUser = underTest.getUserByEmail("manuella@gmail.com");

        assertNotNull(foundUser);
        assertEquals(testData.Existing_User.getEmail(), foundUser.getEmail());
    }

    @Test
    void givenService_registerUser() {
        user newUser = testData.New_User;
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("hashedPassword");
        when(userRepo.save(any(user.class))).thenReturn(newUser);

        user registeredUser = underTest.registerUser(newUser);

        assertNotNull(registeredUser);
        assertEquals("hashedPassword", registeredUser.getPassword());
        verify(userRepo, times(1)).save(any(user.class));
    }
}
