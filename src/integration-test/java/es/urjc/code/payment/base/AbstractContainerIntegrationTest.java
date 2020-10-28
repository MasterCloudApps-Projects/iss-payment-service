package es.urjc.code.payment.base;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractContainerIntegrationTest {
	
	protected static final PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:9.6.15").withDatabaseName("policy")
			.withUsername("postgres").withPassword("password");
	
	protected static KafkaContainer kafka = new KafkaContainer();

	@BeforeAll
	static void setUpAll() {
		if (!postgresContainer.isRunning()) {
		 postgresContainer.start();
		}
		if (!kafka.isRunning()) {
		 kafka.start();
		}
	}
	
	@Test
	void shouldBeRunning() {
		assertTrue(postgresContainer.isRunning());
		assertTrue(kafka.isRunning());
	}
	
	public static class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues
					.of("spring.datasource.driver-class-name=" + postgresContainer.getDriverClassName(),
							"spring.datasource.url=" + postgresContainer.getJdbcUrl(),
							"spring.datasource.username=" + postgresContainer.getUsername(),
							"spring.datasource.password=" + postgresContainer.getPassword(),
							"spring.kafka.bootstrap-servers", kafka.getBootstrapServers())
					.applyTo(configurableApplicationContext.getEnvironment());
		}
	}
}
