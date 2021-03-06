package es.urjc.code.payment.infrastructure.adapter.kafka;

import java.time.LocalDate;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.payment.application.domain.PolicyAccount;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountLoadPort;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountUpdatePort;
import es.urjc.code.payment.application.port.outgoing.PolicyEventConsumerPort;
import es.urjc.code.payment.service.api.v1.events.EventType;
import es.urjc.code.payment.service.api.v1.events.PolicyEvent;
import es.urjc.code.payment.service.api.v1.events.dto.PolicyDto;


@Component
@Transactional
@EnableBinding(Sink.class)
public class PolicyEventConsumerAdapter implements PolicyEventConsumerPort {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyEventConsumerAdapter.class);
	
	private final PolicyAccountLoadPort policyAccountLoadPort;
	private final PolicyAccountUpdatePort policyAccountUpdatePort;
	
	@Autowired
	public PolicyEventConsumerAdapter(PolicyAccountLoadPort policyAccountLoadPort,PolicyAccountUpdatePort policyAccountUpdatePort) {
		this.policyAccountLoadPort = policyAccountLoadPort;
		this.policyAccountUpdatePort = policyAccountUpdatePort;
	}

    @StreamListener(Sink.INPUT)
	@Override
	public void process(Message<PolicyEvent> event, @Header(KafkaHeaders.ACKNOWLEDGMENT ) Acknowledgment  acknowledgment)  {
    	PolicyEvent payload =  event.getPayload();
    	LOGGER.info("payload received {}", payload);
    	if (EventType.REGISTERED.equals(payload.getEventType())) {
    		PolicyAccount account = policyAccountLoadPort.findByPolicyNumber(payload.getPolicy().getNumber());
            if (account==null) {
              createAccount(payload.getPolicy());        	
            }
    	}
        LOGGER.info("Acknowledgment provided");
        acknowledgment.acknowledge();
	}
	
    private void createAccount(PolicyDto policy) {
    	var currentDate = LocalDate.now();
        PolicyAccount newAccount = new PolicyAccount.Builder()
        		                                    .withPolicyNumber(policy.getNumber())
        		                                    .withPolicyAccountNumber(UUID.randomUUID().toString())
        		                                    .withCreated(currentDate)
        		                                    .withUpdated(currentDate)
        		                                    .build();
        newAccount.expectedPayment(policy.getTotalPremium(),policy.getFrom());
        policyAccountUpdatePort.save(newAccount);
    }

}
