package es.urjc.code.payment.service.api.v1.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PolicyAccountDtoList {
	@NotNull
	private List<PolicyAccountDto> accounts;
	
    public List<PolicyAccountDto> getAccounts() {
        return accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyAccountDtoList)) return false;

        PolicyAccountDtoList that = (PolicyAccountDtoList) o;

        return new EqualsBuilder()
                .append(accounts, that.accounts)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(accounts)
                .toHashCode();
    }
    
    public static final class Builder {

        private final PolicyAccountDtoList object;

        public Builder() {
            object = new PolicyAccountDtoList();
        }

        public Builder withAccounts(List<PolicyAccountDto> value) {
            object.accounts = value;
            return this;
        }

        public PolicyAccountDtoList build() {
            return object;
        }

    }
	
}
