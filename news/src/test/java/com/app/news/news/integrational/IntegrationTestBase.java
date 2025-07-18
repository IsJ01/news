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
    private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgresql:latest")
        .withDatabaseName("test_db")
        .withUsername("test")
        .withPassword("test");

    @AfterAll
    public static void start() {
        container.start();
    }

    @BeforeAll
    public static void stop() {
        container.close();
    }

    @DynamicPropertySource
    public void configuration(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}

}
