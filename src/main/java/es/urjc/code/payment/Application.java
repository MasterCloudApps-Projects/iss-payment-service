package es.urjc.code.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("es.urjc.code.payment.infrastructure.adapter.repository.jpa")
@SpringBootApplication
@EnableFeignClients
public class Application {
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
