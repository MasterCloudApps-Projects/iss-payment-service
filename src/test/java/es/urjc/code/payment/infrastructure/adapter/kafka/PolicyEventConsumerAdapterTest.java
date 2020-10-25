package es.urjc.code.payment.infrastructure.adapter.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.payment.application.domain.PolicyAccount;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountLoadPort;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountUpdatePort;
import es.urjc.code.payment.service.api.v1.events.PolicyRegisteredEvent;
import es.urjc.code.payment.service.api.v1.events.dto.PolicyDto;

class PolicyEventConsumerAdapterTest {

	public static final String POLICY_NUMBER = "P1212121";
	private PolicyAccountLoadPort policyAccountLoadPort;
	private PolicyAccountUpdatePort policyAccountUpdatePort;
	private PolicyEventConsumerAdapter sut;
	
	@BeforeEach
	public void setUp() {
		this.policyAccountLoadPort = Mockito.mock(PolicyAccountLoadPort.class);
		this.policyAccountUpdatePort = Mockito.mock(PolicyAccountUpdatePort.class);
		this.sut = new PolicyEventConsumerAdapter(policyAccountLoadPort, policyAccountUpdatePort);
	}
	
	@Test
	void shouldBeHandlePolicyTerminatedEvent() {
		// given
		final PolicyDto dto = getPolicyDto();
		final PolicyRegisteredEvent event = new PolicyRegisteredEvent.Builder().withPolicy(dto).build();
		final PolicyAccount policyAccount = getPolicyAccount();
		when(policyAccountLoadPort.findByPolicyNumber(POLICY_NUMBER)).thenReturn(null);
		doNothing().when(policyAccountUpdatePort).save(policyAccount);
		// when
		this.sut.onPolicyRegistered(event);
		// then
		verify(policyAccountLoadPort).findByPolicyNumber(POLICY_NUMBER);
		verify(policyAccountUpdatePort).save(any());
	}
	
	
	private PolicyDto getPolicyDto() {
		return new PolicyDto.Builder().withNumber(POLICY_NUMBER).withTotalPremium(new BigDecimal(1000)).withFrom(LocalDate.of(2018,1,1)).build();
	}
	
	private PolicyAccount getPolicyAccount() {
		return new PolicyAccount.Builder().build();
	}
}
