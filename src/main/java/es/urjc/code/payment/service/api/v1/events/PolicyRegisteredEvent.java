package es.urjc.code.payment.service.api.v1.events;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.urjc.code.payment.service.api.v1.events.dto.PolicyDto;

public class PolicyRegisteredEvent extends PolicyEvent {
    
	public PolicyRegisteredEvent() {
        // to avoid deserialization exception
    }
    
    private PolicyRegisteredEvent(final Builder builder) {
        setPolicy(builder.policy);

    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyRegisteredEvent)) return false;

        PolicyRegisteredEvent that = (PolicyRegisteredEvent) o;

        return new EqualsBuilder()
                .append(policy, that.policy)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(policy)
                .toHashCode();
    } 
    
    public static final class Builder {
    	private PolicyDto policy;
    	
        public Builder withPolicy(final PolicyDto policy) {
            this.policy = policy;
            return this;
        }
        
        public PolicyRegisteredEvent build() {
            return new PolicyRegisteredEvent(this);
        }
    }
}
