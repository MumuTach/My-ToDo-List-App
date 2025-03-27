package com.inn.backend;

import com.inn.backend.model.todo;
import com.inn.backend.utils.testData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoIntegrationTests {

	private static String url;

	@LocalServerPort
	private int localServerPort;

	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() {
		url = "http://localhost:" + localServerPort + "/todo";
		webTestClient = WebTestClient.bindToServer().baseUrl(url).build();
	}

	@Test
	void integrationTest_For_Finding_Todos_of_a_user() {
		int id = testData.Existing_User.getId();
		List<todo> expectedTodos = testData.todos;

		webTestClient.get()
				.uri(url+ "/" + id)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(todo.class)
				.value(todos -> {
					assertEquals(expectedTodos.size(), todos.size());
					assertEquals(expectedTodos.get(0).getTitle(), todos.get(0).getTitle());
				});
	}

	@Test
	void integrationTest_For_adding_Todos_of_a_user() {
		int id = testData.Existing_User.getId();

		todo newTodo = new todo();
		newTodo.setTitle("Lire un livre");
		newTodo.setDescription("Lire 20 pages d'un livre");
		newTodo.setStatus(false);

		webTestClient.post()
				.uri(url + "/" + "/add/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(newTodo)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$.title").isEqualTo("Lire un livre");

	}

	@Test
	void integrationTest_For_Updating_A_Todo() {
		int todoId = testData.todos.get(0).getId();

		todo updatedTodo = new todo();
		updatedTodo.setTitle("Acheter du lait et du pain");
		updatedTodo.setDescription("Aller au supermarché pour acheter du lait et du pain");
		updatedTodo.setStatus(true);

		webTestClient.put()
				.uri(url + todoId)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(updatedTodo)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.title").isEqualTo("Acheter du lait et du pain");
	}

	@Test
	void integrationTest_For_Deleting_A_Todo() {
		int todoId = testData.todos.get(1).getId();

		webTestClient.delete()
				.uri(url + todoId)
				.exchange()
				.expectStatus().isOk();
	}

}
