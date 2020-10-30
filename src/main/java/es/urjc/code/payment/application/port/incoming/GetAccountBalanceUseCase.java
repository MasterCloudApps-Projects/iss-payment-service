package es.urjc.code.payment.application.port.incoming;

import java.util.Optional;

import es.urjc.code.payment.service.api.v1.dto.PolicyAccountBalanceDto;

public interface GetAccountBalanceUseCase {
	Optional<PolicyAccountBalanceDto> getAccountBalance(String accountNumber);
}
