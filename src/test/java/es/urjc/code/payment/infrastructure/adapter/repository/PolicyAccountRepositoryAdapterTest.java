package es.urjc.code.payment.infrastructure.adapter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.payment.application.domain.PolicyAccount;
import es.urjc.code.payment.infrastructure.adapter.repository.jpa.PolicyAccountJpaRepository;

class PolicyAccountRepositoryAdapterTest {

	public static final String POLICY_ACCOUNT_NUMBER = "1313123";
	public static final String POLICY_NUMBER = "P1212121";
	private static final LocalDate CREATED_DATE = LocalDate.now();
	private PolicyAccountJpaRepository policyAccountJpaRepository;
	private PolicyAccountRepositoryAdapter sut;
	
	@BeforeEach
	public void setUp() {
		this.policyAccountJpaRepository = Mockito.mock(PolicyAccountJpaRepository.class);
		this.sut =  new PolicyAccountRepositoryAdapter(policyAccountJpaRepository);
	}
	
	@Test
	void shouldNotBeFindByPolicyAccountNumber() {
		// given
		when(policyAccountJpaRepository.findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER)).thenReturn(Optional.empty());
		var response = this.sut.findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER);
		// then
		verify(policyAccountJpaRepository).findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER);
		assertNull(response);
	}


	@Test
	void shouldBeFindByPolicyAccountNumber() {
		// given
		when(policyAccountJpaRepository.findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER)).thenReturn(Optional.of(getPolicyAccount()));
		// when
		var response = this.sut.findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER);
		// then
		verify(policyAccountJpaRepository).findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER);
		assertEquals(POLICY_NUMBER, response.getPolicyNumber());
		assertEquals(POLICY_ACCOUNT_NUMBER, response.getPolicyAccountNumber());
		assertEquals(CREATED_DATE, response.getCreated());
		assertEquals(CREATED_DATE.plusDays(1L), response.getUpdated());
	}


	@Test
	void shouldNotBeFindByPolicyNumber() {
		// given
		when(policyAccountJpaRepository.findByPolicyNumber(POLICY_NUMBER)).thenReturn(Optional.empty());
		// when
		var response = this.sut.findByPolicyNumber(POLICY_NUMBER);
		// then
		verify(policyAccountJpaRepository).findByPolicyNumber(POLICY_NUMBER);
		assertNull(response);
	}


	@Test
	void shouldBeFindByPolicyNumber() {
		// given
		when(policyAccountJpaRepository.findByPolicyNumber(POLICY_NUMBER)).thenReturn(Optional.of(getPolicyAccount()));
		// when
		var response = this.sut.findByPolicyNumber(POLICY_NUMBER);
		// then
		verify(policyAccountJpaRepository).findByPolicyNumber(POLICY_NUMBER);
		assertEquals(POLICY_NUMBER, response.getPolicyNumber());
		assertEquals(POLICY_ACCOUNT_NUMBER, response.getPolicyAccountNumber());
		assertEquals(CREATED_DATE, response.getCreated());
		assertEquals(CREATED_DATE.plusDays(1L), response.getUpdated());
	}
	
	@Test
	void shouldBeSavePolicyAccount() {
		// given
		when(policyAccountJpaRepository.save(any())).thenReturn(getPolicyAccount());
		// when
		this.sut.save(any());
		// then
		verify(policyAccountJpaRepository).save(any());
	}
	
	@Test
	void shouldBeFindAll() {
		// given
		final var policyAccountEntities = Arrays.asList(getPolicyAccount());
		when(policyAccountJpaRepository.findAll()).thenReturn(policyAccountEntities);
		// when
		var response = this.sut.findAll();
		// then
		verify(policyAccountJpaRepository).findAll();
		assertEquals(POLICY_NUMBER, response.get(0).getPolicyNumber());
		assertEquals(POLICY_ACCOUNT_NUMBER, response.get(0).getPolicyAccountNumber());
		assertEquals(CREATED_DATE, response.get(0).getCreated());
		assertEquals(CREATED_DATE.plusDays(1L), response.get(0).getUpdated());
	}
	
    private PolicyAccount getPolicyAccount() {
    	return new PolicyAccount.Builder()
				                .withPolicyAccountNumber(POLICY_ACCOUNT_NUMBER)
				                .withPolicyNumber(POLICY_NUMBER)
				                .withCreated(CREATED_DATE)
				                .withUpdated(CREATED_DATE.plusDays(1L))
    			                .build();
    }

}
