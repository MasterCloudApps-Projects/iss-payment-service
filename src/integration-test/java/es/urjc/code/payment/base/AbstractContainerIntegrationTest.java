package es.urjc.code.payment.base;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractContainerIntegrationTest {
	
	protected static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer("postgres:9.6.15").withDatabaseName("payment")
			.withUsername("postgres").withPassword("password");
    private static final KafkaContainer kafkaContainer = new KafkaContainer("5.5.1");

    @DynamicPropertySource
    private static void dynamicProperties(DynamicPropertyRegistry registry) {
        Network network = Network.SHARED;
        
        // PostgreSQL
        postgresContainer.withNetwork(network).withNetworkAliases("postgresql")
        .withUrlParam("characterEncoding", "UTF-8")
        .withUrlParam("serverTimezone", "UTC");
        postgresContainer.start();

        // Kafka
        kafkaContainer.withNetwork(network).withNetworkAliases("kafka")
                .withExternalZookeeper("zookeeper:2181")
                .withExposedPorts(9092, 9093);
        kafkaContainer.start();
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
		registry.add("spring.datasource.password", postgresContainer::getPassword);
		registry.add("spring.datasource.username", postgresContainer::getUsername);
		registry.add("spring.kafka.bootstrap-servers",kafkaContainer::getBootstrapServers);
    }

	@BeforeAll
	static void setUpAll() {
		if (!postgresContainer.isRunning()) {
			 postgresContainer.start();
		}
		if (!kafkaContainer.isRunning()) {
			kafkaContainer.start();
		}		
	}
	
	@Test
	void shouldBeRunning() {
		assertTrue(postgresContainer.isRunning());
		assertTrue(kafkaContainer.isRunning());
	}

}