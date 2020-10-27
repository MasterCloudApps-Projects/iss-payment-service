package es.urjc.code.payment.service.api.v1.dto;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PolicyAccountDto {
    private String policyAccountNumber;
    private String policyNumber;
    private LocalDate created;
    private LocalDate updated;
    
    public String getPolicyAccountNumber() {
        return policyAccountNumber;
    }

    public String getPolicyNumber() {
        return policyNumber;
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

        if (!(o instanceof PolicyAccountDto)) return false;

        PolicyAccountDto that = (PolicyAccountDto) o;

        return new EqualsBuilder()
                .append(policyAccountNumber, that.policyAccountNumber)
                .append(policyNumber, that.policyNumber)
                .append(created, that.created)
                .append(updated, that.updated)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(policyAccountNumber)
                .append(policyNumber)
                .append(created)
                .append(updated)
                .toHashCode();
    }     
    public static final class Builder {

        private final PolicyAccountDto object;

        public Builder() {
            object = new PolicyAccountDto();
        }

        public Builder withPolicyAccountNumber(String value) {
            object.policyAccountNumber = value;
            return this;
        }

        public Builder withPolicyNumber(String value) {
            object.policyNumber = value;
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

        public PolicyAccountDto build() {
            return object;
        }

    }

}
