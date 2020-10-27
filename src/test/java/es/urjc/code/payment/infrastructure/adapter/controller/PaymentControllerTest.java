package es.urjc.code.payment.infrastructure.adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.payment.application.port.incoming.FindAllPolicyAccountUseCase;
import es.urjc.code.payment.application.port.incoming.GetAccountBalanceUSeCase;
import es.urjc.code.payment.service.api.v1.dto.PolicyAccountDtoList;
import es.urjc.code.payment.service.api.v1.dto.PolicyAccountBalanceDto;
import es.urjc.code.payment.service.api.v1.dto.PolicyAccountDto;

class PaymentControllerTest {

	public static final String POLICY_ACCOUNT_NUMBER = "1313123";
	public static final String POLICY_NUMBER = "P1212121";
	private static final LocalDate CREATED_DATE = LocalDate.now();
	
	private FindAllPolicyAccountUseCase findAllPolicyAccountUseCase;
	private GetAccountBalanceUSeCase getAccountBalanceUSeCase;
	private PaymentController sut;
	
	@BeforeEach
	public void setUp() {
		this.findAllPolicyAccountUseCase = Mockito.mock(FindAllPolicyAccountUseCase.class);
		this.getAccountBalanceUSeCase = Mockito.mock(GetAccountBalanceUSeCase.class);
		this.sut =  new PaymentController(findAllPolicyAccountUseCase, getAccountBalanceUSeCase);
	}
	
	@Test
	void shouldBeFindAllAccounts() {
		// given
		final var policyAccountDtos = Arrays.asList(getPolicyAccountDto());
		final PolicyAccountDtoList list = new PolicyAccountDtoList.Builder().withAccounts(policyAccountDtos).build();
		when(findAllPolicyAccountUseCase.findAll()).thenReturn(list);				
		// when
		var response = this.sut.accounts();
		// then
		verify(findAllPolicyAccountUseCase).findAll();
		assertEquals(response.getBody().getAccounts(),policyAccountDtos);
	}
	
	@Test
	void shouldBeGetAccountBalance() {
		// given
		when(getAccountBalanceUSeCase.getAccountBalance(POLICY_ACCOUNT_NUMBER)).thenReturn(getPolicyAccountBalanceDto());
		// when
		var response = this.sut.accountBalance(POLICY_ACCOUNT_NUMBER);
		// then
		verify(getAccountBalanceUSeCase).getAccountBalance(POLICY_ACCOUNT_NUMBER);
		assertEquals(POLICY_ACCOUNT_NUMBER,response.getBody().getPolicyAccountNumber());
		assertEquals(POLICY_NUMBER,response.getBody().getPolicyNumber());
		assertEquals(CREATED_DATE,response.getBody().getCreated());
		assertEquals(CREATED_DATE.plusDays(1L),response.getBody().getUpdated());
	}
	
    private PolicyAccountDto getPolicyAccountDto() {
    	return new PolicyAccountDto.Builder()
				                .withPolicyAccountNumber(POLICY_ACCOUNT_NUMBER)
				                .withPolicyNumber(POLICY_NUMBER)
				                .withCreated(CREATED_DATE)
				                .withUpdated(CREATED_DATE.plusDays(1L))
    			                .build();
    }
    
    private PolicyAccountBalanceDto getPolicyAccountBalanceDto() {
    	return new PolicyAccountBalanceDto.Builder()
                .withPolicyAccountNumber(POLICY_ACCOUNT_NUMBER)
                .withPolicyNumber(POLICY_NUMBER)
                .withCreated(CREATED_DATE)
                .withUpdated(CREATED_DATE.plusDays(1L))
    			.build();
    }
}
