package es.urjc.code.payment.application.port.incoming;

import es.urjc.code.payment.service.api.v1.dto.PolicyAccountBalanceDto;

public interface GetAccountBalanceUSeCase {
	PolicyAccountBalanceDto getAccountBalance(String accountNumber);
}
