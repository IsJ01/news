package com.app.news.news.integrational;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class IntegrationTestBase {

    @Container
    private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("test_db")
        .withUsername("test")
        .withPassword("test");

    @BeforeAll
    public static void start() {
        container.start();
    }

    @AfterAll
    public static void stop() {
        container.close();
    }

    @DynamicPropertySource
    public static void configuration(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}

}
