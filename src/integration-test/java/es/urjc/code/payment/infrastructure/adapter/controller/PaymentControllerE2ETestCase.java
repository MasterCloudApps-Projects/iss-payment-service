package es.urjc.code.payment.infrastructure.adapter.controller;


import static es.urjc.code.payment.infrastructure.adapter.controller.BaseE2ETestCase.Resources.V1_ACCOUNTS_ENDPOINT;
import static es.urjc.code.payment.infrastructure.adapter.controller.BaseE2ETestCase.Resources.V1_ACCOUNTS_GET_BALANCE_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import es.urjc.code.payment.infrastructure.adapter.repository.entity.PolicyAccountEntity;
import es.urjc.code.payment.infrastructure.adapter.repository.jpa.PolicyAccountJpaRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaymentControllerE2ETestCase extends BaseE2ETestCase {

	public static final String POLICY_ACCOUNT_NUMBER = "1313123";
	public static final String POLICY_NUMBER = "P1212121";
	private static final LocalDate CREATED_DATE = LocalDate.now();
	
	@LocalServerPort
	private int port;

	@Autowired
	private PolicyAccountJpaRepository policyAccountJpaRepository;
	
	@BeforeEach
	void setUpBeforeEach() {
		RestAssured.port = this.port;
		
		var pa = policyAccountJpaRepository.findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER);
		if (!pa.isPresent()) {
			PolicyAccountEntity entity = new PolicyAccountEntity.Builder()
	                .withPolicyNumber(POLICY_NUMBER)
	                .withPolicyAccountNumber(POLICY_ACCOUNT_NUMBER)
	                .withCreated(CREATED_DATE)
	                .withUpdated(CREATED_DATE.plusDays(1L))
	                .build();
	        policyAccountJpaRepository.save(entity);			
		}
	}

	@Test
	void shouldBeFindAllAccounts() {
        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .when()
                .get(V1_ACCOUNTS_ENDPOINT.build())
                .prettyPeek()
                .then();
        //then
        response.statusCode(HttpStatus.OK.value())
                .body("accounts", notNullValue());
	}
	
	@Test
	void shouldBeGetAccountBalance() {
    	// given
    	final RequestSpecification requestSpecification = given()
                .log().all()
                .contentType(APPLICATION_JSON_VALUE);
    	
        // when
        final Response response = requestSpecification
                .when()
                .get(V1_ACCOUNTS_GET_BALANCE_ENDPOINT.build());
        
        // then
        response.then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(APPLICATION_JSON_VALUE)
                .body("policyAccountNumber", equalTo(POLICY_ACCOUNT_NUMBER));
        
       
	}
}
