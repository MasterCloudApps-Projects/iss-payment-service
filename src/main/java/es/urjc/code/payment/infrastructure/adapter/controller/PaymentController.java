package es.urjc.code.payment.infrastructure.adapter.controller;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.code.payment.application.port.incoming.FindAllPolicyAccountUseCase;
import es.urjc.code.payment.application.port.incoming.GetAccountBalanceUSeCase;
import es.urjc.code.payment.service.api.v1.dto.PolicyAccountBalanceDto;
import es.urjc.code.payment.service.api.v1.dto.PolicyAccountDtoList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "payment", description = "the Payment API")
public class PaymentController {

	private final FindAllPolicyAccountUseCase findAllPolicyAccountUseCase;
	private final GetAccountBalanceUSeCase getAccountBalanceUSeCase;
	
	@Autowired
	public PaymentController(FindAllPolicyAccountUseCase findAllPolicyAccountUseCase,GetAccountBalanceUSeCase getAccountBalanceUSeCase) {
		this.findAllPolicyAccountUseCase = findAllPolicyAccountUseCase;
		this.getAccountBalanceUSeCase = getAccountBalanceUSeCase;
	}
	
	@Operation(summary = "Find all policy account", description = "Find all policy account", tags = { "payment" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PolicyAccountDtoList.class)))) })
	@GetMapping("/api/v1/accounts")
	public ResponseEntity<PolicyAccountDtoList> accounts() {
		return ResponseEntity.status(HttpStatus.OK).body(findAllPolicyAccountUseCase.findAll());
	}
	
	@Operation(summary = "Find policy account balance by account number", description = "Returns a policy account balancet", tags = { "payment" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PolicyAccountBalanceDto.class))),
			@ApiResponse(responseCode = "404", description = "Product not found") })
	@GetMapping("/api/v1/accounts/{accountNumber}")
	public ResponseEntity<PolicyAccountBalanceDto> accountBalance(@Parameter(description = "Account number. Cannot be empty.", required = true) @PathVariable("accountNumber") @NotEmpty String accountNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(getAccountBalanceUSeCase.getAccountBalance(accountNumber));
	}
}
