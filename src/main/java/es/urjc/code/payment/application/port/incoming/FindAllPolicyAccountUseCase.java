package es.urjc.code.payment.application.port.incoming;

import java.util.List;

import es.urjc.code.payment.service.api.v1.dto.PolicyAccountDto;

public interface FindAllPolicyAccountUseCase {
	List<PolicyAccountDto> findAll();
}
