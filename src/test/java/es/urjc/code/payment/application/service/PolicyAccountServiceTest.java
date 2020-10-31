package es.urjc.code.payment.application.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.payment.application.domain.PolicyAccount;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountLoadPort;

class PolicyAccountServiceTest {
	
	public static final String POLICY_ACCOUNT_NUMBER = "1313123";
	public static final String POLICY_NUMBER = "P1212121";
	private static final LocalDate CREATED_DATE = LocalDate.now();
	
	private PolicyAccountLoadPort policyAccountLoadPort;
    private PolicyAccountService sut;
    
	@BeforeEach
	public void setUp() {
		this.policyAccountLoadPort = Mockito.mock(PolicyAccountLoadPort.class);
		this.sut = new PolicyAccountService(policyAccountLoadPort);
	}
	
	@Test
	void shouldBeGetAccountBalance() {
		// given
	    when(policyAccountLoadPort.findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER)).thenReturn(getPolicyAccount());
	    // when
	 	var response = this.sut.getAccountBalance(POLICY_ACCOUNT_NUMBER);
	 	// then
	 	verify(policyAccountLoadPort).findByPolicyAccountNumber(POLICY_ACCOUNT_NUMBER);
	 	assertTrue(response.isPresent());
	}
	
	@Test
	void shouldBeFindAll() {
		// given
		final var policyAccounts = Arrays.asList(getPolicyAccount());
		when(policyAccountLoadPort.findAll()).thenReturn(policyAccounts);
		// when
		var response = this.sut.findAll();
		// then
		verify(policyAccountLoadPort).findAll();
		assertEquals(POLICY_NUMBER, response.getAccounts().get(0).getPolicyNumber());
		assertEquals(POLICY_ACCOUNT_NUMBER, response.getAccounts().get(0).getPolicyAccountNumber());
		assertEquals(CREATED_DATE, response.getAccounts().get(0).getCreated());
		assertEquals(CREATED_DATE.plusDays(1L), response.getAccounts().get(0).getUpdated());
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
