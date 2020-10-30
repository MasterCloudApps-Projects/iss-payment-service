package es.urjc.code.payment.infrastructure.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.payment.application.domain.AccountingEntry;
import es.urjc.code.payment.application.domain.ExpectedPayment;
import es.urjc.code.payment.application.domain.InPayment;
import es.urjc.code.payment.application.domain.OutPayment;
import es.urjc.code.payment.application.domain.PolicyAccount;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountLoadPort;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountUpdatePort;
import es.urjc.code.payment.infrastructure.adapter.repository.entity.AccountingEntryEntity;
import es.urjc.code.payment.infrastructure.adapter.repository.entity.ExpectedPaymentEntity;
import es.urjc.code.payment.infrastructure.adapter.repository.entity.InPaymentEntity;
import es.urjc.code.payment.infrastructure.adapter.repository.entity.OutPaymentEntity;
import es.urjc.code.payment.infrastructure.adapter.repository.entity.PolicyAccountEntity;
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
		Optional<PolicyAccountEntity> optEntity = policyAccountJpaRepository
				.findByPolicyAccountNumber(policyAccountNumber);
		if (optEntity.isPresent()) {
			return mapToPolicyAccount(optEntity.get());
		}
		return null;
	}

	@Override
	public PolicyAccount findByPolicyNumber(String policyNumber) {
		Optional<PolicyAccountEntity> optEntity = policyAccountJpaRepository.findByPolicyNumber(policyNumber);
		if (optEntity.isPresent()) {
			return mapToPolicyAccount(optEntity.get());
		}
		return null;
	}

	@Override
	public void save(PolicyAccount policyAccount) {
		policyAccountJpaRepository.save(mapToPolicyAccountEntity(policyAccount));
	}

	@Override
	public List<PolicyAccount> findAll() {
		var policyAccountEntities = policyAccountJpaRepository.findAll();
		return policyAccountEntities.stream().map(this::mapToPolicyAccount).collect(Collectors.toList());
	}

	private PolicyAccount mapToPolicyAccount(PolicyAccountEntity entity) {
		return new PolicyAccount.Builder().withPolicyNumber(entity.getPolicyNumber())
				.withPolicyAccountNumber(entity.getPolicyAccountNumber()).withCreated(entity.getCreated())
				.withUpdated(entity.getUpdated())
				.withEntries(entity.getEntries().stream().map(this::mapToAccountingEntry).collect(Collectors.toSet()))
				.build();
	}

	private PolicyAccountEntity mapToPolicyAccountEntity(PolicyAccount entity) {

		return new PolicyAccountEntity.Builder().withPolicyNumber(entity.getPolicyNumber())
				.withPolicyAccountNumber(entity.getPolicyAccountNumber()).withCreated(entity.getCreated())
				.withUpdated(entity.getUpdated()).build();
	}

	private AccountingEntry mapToAccountingEntry(AccountingEntryEntity entity) {
		AccountingEntry accountingEntry = null;
		if (entity instanceof ExpectedPaymentEntity) {
			accountingEntry = new ExpectedPayment.Builder().withAmount(entity.getAmount())
					.withCreationDate(entity.getCreationDate()).withEffectiveDate(entity.getEffectiveDate()).build();
		} else if (entity instanceof InPaymentEntity) {
			accountingEntry = new InPayment.Builder().withAmount(entity.getAmount())
					.withCreationDate(entity.getCreationDate()).withEffectiveDate(entity.getEffectiveDate()).build();
		} else if (entity instanceof OutPaymentEntity) {
			accountingEntry = new OutPayment.Builder().withAmount(entity.getAmount())
					.withCreationDate(entity.getCreationDate()).withEffectiveDate(entity.getEffectiveDate()).build();
		}
		return accountingEntry;
	}
}
