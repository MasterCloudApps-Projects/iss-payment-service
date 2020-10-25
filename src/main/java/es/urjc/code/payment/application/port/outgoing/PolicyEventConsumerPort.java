package es.urjc.code.payment.application.port.outgoing;

import es.urjc.code.payment.service.api.v1.events.PolicyRegisteredEvent;

public interface PolicyEventConsumerPort {

	public void onPolicyRegistered(PolicyRegisteredEvent event);
	
}
