package com.inn.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {

	private static String url;

	@LocalServerPort
	private int localServerPort;

	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() {
		url = "http://localhost:" + localServerPort + "/api/weather";
		webTestClient = WebTestClient.bindToServer().baseUrl(url).build();
	}

	@Test
	void contextLoads() {
	}

}
