package es.urjc.code.payment.application.port.outgoing;

import org.springframework.messaging.Message;

import es.urjc.code.payment.service.api.v1.events.PolicyEvent;

public interface PolicyEventConsumerPort {

	public void process(Message<PolicyEvent> event);
	
}
