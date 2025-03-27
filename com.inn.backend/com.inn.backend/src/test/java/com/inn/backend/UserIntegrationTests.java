package com.inn.backend;

import com.inn.backend.model.todo;
import com.inn.backend.model.user;
import com.inn.backend.utils.testData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTests {

    private static String url;

    @LocalServerPort
    private int localServerPort;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        url = "http://localhost:" + localServerPort + "/USER";
        webTestClient = WebTestClient.bindToServer().baseUrl(url).build();
    }

    @Test
    void integrationTest_for_finding_a_user_by_email() {
        String email = testData.Existing_User.getEmail();
        user expectedUser = testData.Existing_User;

        webTestClient.get()
                .uri(url+ "/email/" + email)
                .exchange()
                .expectStatus().isOk()
                .expectBody(user.class)
                .value(user -> {
                    assertEquals(expectedUser.getEmail(), user.getEmail());
                });
    }

    @Test
    void integrationTest_for_finding_a_user_by_id() {
        int id = testData.Existing_User.getId();
        user expectedUser = testData.Existing_User;

        webTestClient.get()
                .uri(url+ "/id/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(user.class)
                .value(user -> {
                    assertEquals(expectedUser.getId(), user.getId());
                });
    }

    @Test
    void integrationTest_For_User_Login_Success() {
        user loginUser = testData.Existing_User;
        Map<String, String> loginData = Map.of(
                "email", loginUser.getEmail(),
                "password", loginUser.getPassword()
        );

        webTestClient.post()
                .uri(url + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginData)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.message").isEqualTo("Login successful");
    }

    @Test
    void integrationTest_For_User_SignUp_Success() {
        user newUser = new user();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("newPassword123");

        webTestClient.post()
                .uri(url + "/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.message").isEqualTo("User registered successfully");
    }

    @Test
    void integrationTest_For_User_Logout() {
        webTestClient.post()
                .uri(url + "/logout")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Logged out successfully");
    }

}
