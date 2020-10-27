package es.urjc.code.payment.application.port.incoming;

import es.urjc.code.payment.service.api.v1.dto.PolicyAccountDtoList;

public interface FindAllPolicyAccountUseCase {
	
	PolicyAccountDtoList findAll();
}
