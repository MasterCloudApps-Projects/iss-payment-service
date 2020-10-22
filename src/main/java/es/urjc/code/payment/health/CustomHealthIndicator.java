package es.urjc.code.payment.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

	private final String serviceName;
	
	@Autowired
	public CustomHealthIndicator(@Value("${spring.application.name}") String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Override
	public Health health() {
		return Health.up().withDetail(serviceName, serviceName + " Available").build();
	}

}
