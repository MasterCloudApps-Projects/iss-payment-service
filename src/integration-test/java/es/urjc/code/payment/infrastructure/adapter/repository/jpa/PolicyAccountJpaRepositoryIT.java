package es.urjc.code.payment.infrastructure.adapter.repository.jpa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import es.urjc.code.payment.base.AbstractContainerBaseTest;
import es.urjc.code.payment.infrastructure.adapter.repository.entity.PolicyAccountEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(initializers = { AbstractContainerBaseTest.PropertiesInitializer.class })
class PolicyAccountJpaRepositoryIT extends AbstractContainerBaseTest {
	
	private static final String POLICY_ACCOUNT_NUMBER_NOT_EXIST = "NOT_EXIST";
	private static final String POLICY_NUMBER_NOT_EXIST = "NOT_EXIST";
	private static final LocalDate CREATED_DATE = LocalDate.now();
	private static final String POLICY_ACCOUNT_NUMBER = "B";
	private static final String POLICY_NUMBER = "A";

	@Autowired
	private PolicyAccountJpaRepository policyAccountJpaRepository;
	
	private PolicyAccountEntity entity;

	@BeforeEach
	public void setUp() {
		this.entity = new PolicyAccountEntity.Builder()
				                             .withPolicyNumber(POLICY_NUMBER)
				                             .withPolicyAccountNumber(POLICY_ACCOUNT_NUMBER)
				                             .withCreated(CREATED_DATE)
				                             .withUpdated(CREATED_DATE.plusDays(1L))
				                             .build();
		policyAccountJpaRepository.save(entity);
	}
	
	@Test
	void testWhenFindByPolicyAccountNumberThenReturnEntity() {
		var p = policyAccountJpaRepository.findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER);
		assertTrue(p.isPresent());
	}
	
	@Test
	void testWhenFindByPolicyAccountNumberThenNotReturnEntity() {
		var p = policyAccountJpaRepository.findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER_NOT_EXIST);
		assertTrue(!p.isPresent());
	}
	
	@Test
	void testWhenFindByPolicyNumberThenReturnEntity() {
		var p = policyAccountJpaRepository.findByPolicyNumber(POLICY_NUMBER);
		assertTrue(p.isPresent());
	}
	
	@Test
	void testWhenFindByPolicyNumberThenNotReturnEntity() {
		var p = policyAccountJpaRepository.findByPolicyAccountNumber(POLICY_NUMBER_NOT_EXIST);
		assertTrue(!p.isPresent());
	}
}
