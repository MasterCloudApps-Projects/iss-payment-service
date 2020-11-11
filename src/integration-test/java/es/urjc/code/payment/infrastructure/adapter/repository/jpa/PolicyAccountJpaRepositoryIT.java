package es.urjc.code.payment.infrastructure.adapter.repository.jpa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import es.urjc.code.payment.application.domain.ExpectedPayment;
import es.urjc.code.payment.application.domain.InPayment;
import es.urjc.code.payment.application.domain.OutPayment;
import es.urjc.code.payment.application.domain.PolicyAccount;
import es.urjc.code.payment.base.AbstractContainerBaseTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(initializers = { AbstractContainerBaseTest.PropertiesInitializer.class })
@ActiveProfiles("test")
class PolicyAccountJpaRepositoryIT extends AbstractContainerBaseTest {
	
	private static final String POLICY_ACCOUNT_NUMBER_NOT_EXIST = "NOT_EXIST";
	private static final String POLICY_NUMBER_NOT_EXIST = "NOT_EXIST";
	private static final LocalDate CREATED_DATE = LocalDate.now();
	private static final String POLICY_ACCOUNT_NUMBER = "B";
	private static final String POLICY_NUMBER = "A";

	@Autowired
	private PolicyAccountJpaRepository policyAccountJpaRepository;
	
	@BeforeEach
	public void setUp() {
		PolicyAccount entity = new PolicyAccount.Builder()
				                             .withPolicyNumber(POLICY_NUMBER)
				                             .withPolicyAccountNumber(POLICY_ACCOUNT_NUMBER)
				                             .withCreated(CREATED_DATE)
				                             .withUpdated(CREATED_DATE.plusDays(1L))
				                             .build();
		entity.addEntry(getExpectedPayment());
		entity.addEntry(getInPayment());
		entity.addEntry(getOutPayment());
		policyAccountJpaRepository.save(entity);
	}
	
	@Test
	void testWhenFindByPolicyAccountNumberThenReturn() {
		var p = policyAccountJpaRepository.findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER);
		assertTrue(p.isPresent());
	}
	
	@Test
	void testWhenFindByPolicyAccountNumberThenNotReturn() {
		var p = policyAccountJpaRepository.findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER_NOT_EXIST);
		assertTrue(!p.isPresent());
	}
	
	@Test
	void testWhenFindByPolicyNumberThenReturn() {
		var p = policyAccountJpaRepository.findByPolicyNumber(POLICY_NUMBER);
		assertTrue(p.isPresent());
	}
	
	@Test
	void testWhenFindByPolicyNumberThenNotReturn() {
		var p = policyAccountJpaRepository.findByPolicyAccountNumber(POLICY_NUMBER_NOT_EXIST);
		assertTrue(!p.isPresent());
	}
	
	private ExpectedPayment getExpectedPayment() {
		return new ExpectedPayment.Builder().withAmount(new BigDecimal(10))
		.withCreationDate(CREATED_DATE).withEffectiveDate(CREATED_DATE).build();
	}

	private InPayment getInPayment() {
		return new InPayment.Builder().withAmount(new BigDecimal(10))
		.withCreationDate(CREATED_DATE).withEffectiveDate(CREATED_DATE).build();
	}
	
	private OutPayment getOutPayment() {
		return new OutPayment.Builder().withAmount(new BigDecimal(10))
		.withCreationDate(CREATED_DATE).withEffectiveDate(CREATED_DATE).build();
	}
}
