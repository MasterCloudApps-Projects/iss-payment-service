package es.urjc.code.payment.infrastructure.adapter.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.payment.application.domain.PolicyAccount;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountLoadPort;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountUpdatePort;
import es.urjc.code.payment.application.port.outgoing.PolicyEventConsumerPort;
import es.urjc.code.payment.service.api.v1.events.PolicyRegisteredEvent;
import es.urjc.code.payment.service.api.v1.events.dto.PolicyDto;

@Service
@Transactional
public class PolicyEventConsumerAdapter implements PolicyEventConsumerPort {
	
	private final PolicyAccountLoadPort policyAccountLoadPort;
	private final PolicyAccountUpdatePort policyAccountUpdatePort;
	
	@Autowired
	public PolicyEventConsumerAdapter(PolicyAccountLoadPort policyAccountLoadPort,PolicyAccountUpdatePort policyAccountUpdatePort) {
		this.policyAccountLoadPort = policyAccountLoadPort;
		this.policyAccountUpdatePort = policyAccountUpdatePort;
	}

	@StreamListener(PoliciesStreams.INPUT_POLICY_REGISTRED)
	@Override
	public void onPolicyRegistered(PolicyRegisteredEvent event) {
		PolicyAccount account = policyAccountLoadPort.findByPolicyNumber(event.getPolicy().getNumber());
        if (account==null) {
          createAccount(event.getPolicy());        	
        }
	}
	
    private void createAccount(PolicyDto policy) {
        PolicyAccount newAccount = new PolicyAccount();
        newAccount.expectedPayment(policy.getTotalPremium(),policy.getFrom());
        policyAccountUpdatePort.save(newAccount);
    }

}
