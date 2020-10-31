package es.urjc.code.payment.infrastructure.adapter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.payment.application.domain.PolicyAccount;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountLoadPort;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountUpdatePort;
import es.urjc.code.payment.infrastructure.adapter.repository.jpa.PolicyAccountJpaRepository;

@Service
@Transactional
public class PolicyAccountRepositoryAdapter implements PolicyAccountLoadPort, PolicyAccountUpdatePort {

	private final PolicyAccountJpaRepository policyAccountJpaRepository;

	@Autowired
	public PolicyAccountRepositoryAdapter(PolicyAccountJpaRepository policyAccountJpaRepository) {
		this.policyAccountJpaRepository = policyAccountJpaRepository;
	}

	@Override
	public PolicyAccount findByPolicyAccountNumber(String policyAccountNumber) {
		Optional<PolicyAccount> opt = policyAccountJpaRepository
				.findByPolicyAccountNumber(policyAccountNumber);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public PolicyAccount findByPolicyNumber(String policyNumber) {
		Optional<PolicyAccount> opt = policyAccountJpaRepository.findByPolicyNumber(policyNumber);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public void save(PolicyAccount policyAccount) {
		policyAccountJpaRepository.save(policyAccount);
	}

	@Override
	public List<PolicyAccount> findAll() {
		return policyAccountJpaRepository.findAll();
	}

}
