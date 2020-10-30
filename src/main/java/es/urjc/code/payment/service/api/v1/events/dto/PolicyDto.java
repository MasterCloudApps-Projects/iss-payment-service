package es.urjc.code.payment.service.api.v1.events.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class PolicyDto {
	
	@NotNull
	private UUID id;
	@NotNull
    private String number;
	@NotNull
    private LocalDate from;
	@NotNull
    private LocalDate to;
	@NotNull
    private String policyHolder;
	@NotEmpty
    private String productCode;
	@NotNull
    private BigDecimal totalPremium;
    @NotEmpty
    private String agentLogin;
        
    public UUID getId() {
		return id;
	}

	public String getNumber() {
        return number;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public String getProductCode() {
        return productCode;
    }

    public BigDecimal getTotalPremium() {
        return totalPremium;
    }

    public String getAgentLogin() {
        return agentLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyDto)) return false;

        PolicyDto that = (PolicyDto) o;

        return new EqualsBuilder()
        		.append(id, that.id)
                .append(number, that.number)
                .append(from, that.from)
                .append(to, that.to)
                .append(policyHolder, that.policyHolder)
                .append(productCode, that.productCode)
                .append(totalPremium, that.totalPremium)
                .append(agentLogin, that.agentLogin)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
        		.append(id)
                .append(number)
                .append(from)
                .append(to)
                .append(policyHolder)
                .append(productCode)
                .append(totalPremium)
                .append(agentLogin)
                .toHashCode();
    }
    
    public static final class Builder {

        private final PolicyDto object;

        public Builder() {
            object = new PolicyDto();
        }
        
        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withNumber(String value) {
            object.number = value;
            return this;
        }

        public Builder withFrom(LocalDate value) {
            object.from = value;
            return this;
        }

        public Builder withTo(LocalDate value) {
            object.to = value;
            return this;
        }

        public Builder withPolicyHolder(String value) {
            object.policyHolder = value;
            return this;
        }

        public Builder withProductCode(String value) {
            object.productCode = value;
            return this;
        }

        public Builder withTotalPremium(BigDecimal value) {
            object.totalPremium = value;
            return this;
        }

        public Builder withAgentLogin(String value) {
            object.agentLogin = value;
            return this;
        }

        public PolicyDto build() {
            return object;
        }

    }
}
