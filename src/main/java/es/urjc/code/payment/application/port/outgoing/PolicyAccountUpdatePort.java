package es.urjc.code.payment.application.port.outgoing;

import es.urjc.code.payment.application.domain.PolicyAccount;

public interface PolicyAccountUpdatePort {

	public void save(PolicyAccount policyAccount); 
}
