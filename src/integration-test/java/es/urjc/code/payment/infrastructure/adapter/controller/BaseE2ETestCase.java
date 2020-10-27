package es.urjc.code.payment.infrastructure.adapter.controller;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public abstract class BaseE2ETestCase {

	
	@Container
	public static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:9.6.15").withDatabaseName("pricing")
			.withUsername("postgres").withPassword("password");

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
		registry.add("spring.datasource.password", postgresContainer::getPassword);
		registry.add("spring.datasource.username", postgresContainer::getUsername);
	}

	@BeforeAll
	static void setUpAll() {
		if (!postgresContainer.isRunning()) {
			 postgresContainer.start();
		}
	}
	
    enum Resources {
        V1_ACCOUNTS_ENDPOINT("/api/v1/accounts"),
        V1_ACCOUNTS_GET_BALANCE_ENDPOINT("/api/v1/accounts/1313123");

        private final String endpoint;

        Resources(String endpoint) {
            this.endpoint = endpoint;
        }

        public String build() {
            return endpoint;
        }
    }
}
