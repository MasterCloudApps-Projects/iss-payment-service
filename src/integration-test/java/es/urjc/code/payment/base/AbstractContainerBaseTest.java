package es.urjc.code.payment.base;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractContainerBaseTest {

	protected static final PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:9.6.15").withDatabaseName("payment")
			.withUsername("postgres").withPassword("password");

	@BeforeAll
	static void setUpAll() {
		if (!postgresContainer.isRunning()) {
		 postgresContainer.start();
		}
	}
	
	
	public static class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues
					.of("spring.datasource.driver-class-name=" + postgresContainer.getDriverClassName(),
							"spring.datasource.url=" + postgresContainer.getJdbcUrl(),
							"spring.datasource.username=" + postgresContainer.getUsername(),
							"spring.datasource.password=" + postgresContainer.getPassword())
					.applyTo(configurableApplicationContext.getEnvironment());
		}
	}
}