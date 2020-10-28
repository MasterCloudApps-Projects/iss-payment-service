package es.urjc.code.payment.infrastructure.adapter.kafka;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
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
	
	private final PolicyAccountLoadPort policyAccountLoadPort;
	private final PolicyAccountUpdatePort policyAccountUpdatePort;
	
	@Autowired
	public PolicyEventConsumerAdapter(PolicyAccountLoadPort policyAccountLoadPort,PolicyAccountUpdatePort policyAccountUpdatePort) {
		this.policyAccountLoadPort = policyAccountLoadPort;
		this.policyAccountUpdatePort = policyAccountUpdatePort;
	}

    @StreamListener(Sink.INPUT)
	@Override
	public void process(Message<PolicyEvent> event)  {
    	PolicyEvent payload =  event.getPayload();
    	if (EventType.REGISTERED.equals(payload.getEventType())) {
    		PolicyAccount account = policyAccountLoadPort.findByPolicyNumber(payload.getPolicy().getNumber());
            if (account==null) {
              createAccount(payload.getPolicy());        	
            }
    	}
	}
	
    private void createAccount(PolicyDto policy) {
        PolicyAccount newAccount = new PolicyAccount.Builder().withPolicyNumber(policy.getNumber()).withPolicyAccountNumber(UUID.randomUUID().toString()).build();
        newAccount.expectedPayment(policy.getTotalPremium(),policy.getFrom());
        policyAccountUpdatePort.save(newAccount);
    }

}
