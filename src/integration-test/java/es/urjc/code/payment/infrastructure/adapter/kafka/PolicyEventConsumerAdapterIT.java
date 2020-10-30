package es.urjc.code.payment.infrastructure.adapter.kafka;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.MimeTypeUtils;

import es.urjc.code.payment.Application;
import es.urjc.code.payment.base.AbstractContainerIntegrationTest;
import es.urjc.code.payment.infrastructure.adapter.repository.jpa.PolicyAccountJpaRepository;
import es.urjc.code.payment.service.api.v1.events.EventType;
import es.urjc.code.payment.service.api.v1.events.PolicyEvent;
import es.urjc.code.payment.service.api.v1.events.dto.PolicyDto;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
class PolicyEventConsumerAdapterIT extends AbstractContainerIntegrationTest {

	private static final String POLICY_HOLDER = "François Poirier";
	private static final String PRODUCT_CODE = "CAR";
	private static final String ADMIN_AGENT = "admin";
	public static final String POLICY_NUMBER = "P1212121";
	private static final UUID POLICY_ID = UUID.randomUUID();
 
	@Autowired
	private Sink channels;
	
	@Autowired
	private PolicyAccountJpaRepository policyAccountJpaRepository;
		
	@Test
	void shouldConsumePolicyEventAndSave() {
		// given
		var optPolicyAccount = policyAccountJpaRepository.findByPolicyNumber(POLICY_NUMBER);
		assertTrue(!optPolicyAccount.isPresent());
		
		// when
		PolicyDto policyDto = getPolicyDto();
		PolicyEvent event = new PolicyEvent.Builder().withEventId(UUID.randomUUID().toString())
				.withEventTimestamp(System.currentTimeMillis()).withEventType(EventType.REGISTERED)
				.withPolicy(policyDto).withPolicyId(policyDto.getId()).build();
		Message<PolicyEvent> message = MessageBuilder.withPayload(event)
				.setHeader("partitionKey", policyDto.getId())
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build();
		this.channels.input().send(message);
		// then
		optPolicyAccount = policyAccountJpaRepository.findByPolicyNumber(POLICY_NUMBER);
		assertTrue(optPolicyAccount.isPresent());
	}
	
	private PolicyDto getPolicyDto() {
		return new PolicyDto.Builder().withId(POLICY_ID)
				                      .withAgentLogin(ADMIN_AGENT)
				                      .withFrom(LocalDate.of(2018, 1, 1))
				                      .withNumber(POLICY_NUMBER)
				                      .withProductCode(PRODUCT_CODE)
				                      .withPolicyHolder(POLICY_HOLDER)
				                      .withTo(LocalDate.of(2018, 12, 31))
				                      .withTotalPremium( new BigDecimal(1000))
				                      .build();
	}
}
