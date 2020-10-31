package es.urjc.code.payment.health;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;

class CustomHealthIndicatorTest {

	private static final String SERVICE_AVAILABLE = " Available";
	private static final String MY_SERVICE = "myService";
	private CustomHealthIndicator sut;
	
	@BeforeEach
	public void setUp() {
		this.sut =  new CustomHealthIndicator(MY_SERVICE);
	}
	
	@Test
	void testHealthCheck() {
		final Health health = this.sut.health();
		assertNotNull(health.getStatus());
		assertEquals(MY_SERVICE + SERVICE_AVAILABLE, health.getDetails().get(MY_SERVICE));
	}
}
