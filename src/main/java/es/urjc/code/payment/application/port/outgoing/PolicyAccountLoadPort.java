package es.urjc.code.payment.application.port.outgoing;

import java.util.List;

import es.urjc.code.payment.application.domain.PolicyAccount;

public interface PolicyAccountLoadPort {

	public PolicyAccount findByPolicyAccountNumber(String policyAccountNumber);
	
	public PolicyAccount findByPolicyNumber(String policyNumber);
	
	public List<PolicyAccount> findAll();

}
