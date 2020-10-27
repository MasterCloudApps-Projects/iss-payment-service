package es.urjc.code.payment.application.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.payment.application.domain.PolicyAccount;
import es.urjc.code.payment.application.port.incoming.FindAllPolicyAccountUseCase;
import es.urjc.code.payment.application.port.incoming.GetAccountBalanceUSeCase;
import es.urjc.code.payment.application.port.outgoing.PolicyAccountLoadPort;
import es.urjc.code.payment.service.api.v1.dto.PolicyAccountDtoList;
import es.urjc.code.payment.service.api.v1.dto.PolicyAccountBalanceDto;
import es.urjc.code.payment.service.api.v1.dto.PolicyAccountDto;

@Service
@Transactional
public class PolicyAccountService implements FindAllPolicyAccountUseCase ,GetAccountBalanceUSeCase {
	
	private final PolicyAccountLoadPort policyAccountLoadPort;
	
	@Autowired
	public PolicyAccountService(PolicyAccountLoadPort policyAccountLoadPort) {
		this.policyAccountLoadPort = policyAccountLoadPort;
	}

	@Override
	public PolicyAccountBalanceDto getAccountBalance(String accountNumber) {
		PolicyAccount account = policyAccountLoadPort.findByPolicyAccountNumber(accountNumber);
		return new PolicyAccountBalanceDto.Builder()
				                          .withPolicyNumber(account.getPolicyNumber())
				                          .withPolicyAccountNumber(account.getPolicyAccountNumber())
				                          .withBalance(account.balanceAt(LocalDate.now()))
				                          .withCreated( account.getCreated())
				                          .withUpdated(account.getUpdated())
				                          .build();
	}

	@Override
	public PolicyAccountDtoList findAll() {
		var policyAccounts = policyAccountLoadPort.findAll();
		return new PolicyAccountDtoList.Builder().withAccounts(policyAccounts.stream().map(this::mapToDto).collect(Collectors.toList())).build();
	}

    private PolicyAccountDto mapToDto(PolicyAccount domain){
        return new PolicyAccountDto.Builder()
        		                   .withPolicyNumber(domain.getPolicyNumber())
        		                   .withPolicyAccountNumber(domain.getPolicyAccountNumber())
        		                   .withCreated(domain.getCreated())
        		                   .withUpdated(domain.getUpdated())
        		                   .build();
    }
}
