package es.urjc.code.payment.service.api.v1.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PolicyAccountBalanceDto {
    private String policyAccountNumber;
    private String policyNumber;
    private BigDecimal balance;
    private LocalDate created;
    private LocalDate updated;
    
    public String getPolicyAccountNumber() {
        return policyAccountNumber;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyAccountBalanceDto)) return false;

        PolicyAccountBalanceDto that = (PolicyAccountBalanceDto) o;

        return new EqualsBuilder()
                .append(policyAccountNumber, that.policyAccountNumber)
                .append(policyNumber, that.policyNumber)
                .append(balance, that.balance)
                .append(created, that.created)
                .append(updated, that.updated)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(policyAccountNumber)
                .append(policyNumber)
                .append(balance)
                .append(created)
                .append(updated)
                .toHashCode();
    } 
    
    public static final class Builder {

        private final PolicyAccountBalanceDto object;

        public Builder() {
            object = new PolicyAccountBalanceDto();
        }

        public Builder withPolicyAccountNumber(String value) {
            object.policyAccountNumber = value;
            return this;
        }

        public Builder withPolicyNumber(String value) {
            object.policyNumber = value;
            return this;
        }

        public Builder withBalance(BigDecimal value) {
            object.balance = value;
            return this;
        }

        public Builder withCreated(LocalDate value) {
            object.created = value;
            return this;
        }

        public Builder withUpdated(LocalDate value) {
            object.updated = value;
            return this;
        }

        public PolicyAccountBalanceDto build() {
            return object;
        }

    }    
}
