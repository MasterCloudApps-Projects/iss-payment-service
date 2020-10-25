package es.urjc.code.payment.application.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class OutPayment extends AccountingEntry {

	public OutPayment() {
        // to avoid deserialization exception
    }
    
    private OutPayment(final Builder builder) {
        setId(builder.id);
        setPolicyAccount(builder.policyAccount);
        setAmount(builder.amount);
        setCreationDate(builder.creationDate);
        setEffectiveDate(builder.effectiveDate);
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OutPayment)) return false;

        OutPayment that = (OutPayment) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(policyAccount, that.policyAccount)
                .append(amount, that.amount)
                .append(creationDate, that.creationDate)
                .append(effectiveDate, that.effectiveDate)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(policyAccount)
                .append(amount)
                .append(creationDate)
                .append(effectiveDate)
                .toHashCode();
    }    
    
    public static final class Builder {
    	
        private UUID id;
        private PolicyAccount policyAccount;
        private LocalDate creationDate;
        private LocalDate effectiveDate;
        private BigDecimal amount;

        public Builder withId(final UUID id) {
            this.id = id;
            return this;
        }
        
        public Builder withPolicyAccount(PolicyAccount policyAccount ) {
        	this.policyAccount = policyAccount;
        	return this;	
        }
        
        public Builder withCreationDate(LocalDate creationDate) {
        	this.creationDate = creationDate;
        	return this;	
        }
        
        public Builder withEffectiveDate(LocalDate effectiveDate) {
        	this.effectiveDate = effectiveDate;
        	return this;	
        }
        
        public Builder withAmount (BigDecimal amount) {
        	this.amount = amount;
        	return this;
        }
        
        public OutPayment build() {
            return new OutPayment(this);
        }
    }	
    
	@Override
	public BigDecimal apply(BigDecimal state) {
		 return state.subtract(getAmount());
	}

}
