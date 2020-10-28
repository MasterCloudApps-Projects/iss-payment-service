package es.urjc.code.payment.service.api.v1.events;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.urjc.code.payment.service.api.v1.events.dto.PolicyDto;

public class PolicyEvent {
	
	@NotNull
    private String eventId;
	@NotNull
    private Long eventTimestamp;
	@NotNull
    private EventType eventType;    
	@NotNull
    private UUID policyId;
	@NotNull
	private PolicyDto policy;

    public String getEventId() {
        return eventId;
    }

    public Long getEventTimestamp() {
        return eventTimestamp;
    }

    public EventType getEventType() {
        return eventType;
    }

    public UUID getPolicyId() {
        return policyId;
    }

    public PolicyDto getPolicy() {
        return policy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyEvent)) return false;

        PolicyEvent that = (PolicyEvent) o;

        return new EqualsBuilder()
        		.append(eventId, that.eventId)
                .append(eventTimestamp, that.eventTimestamp)
                .append(eventType, that.eventType)
                .append(policyId, that.policyId)
                .append(policy, that.policy)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
        		.append(eventId)
                .append(eventTimestamp)
                .append(eventType)
                .append(policyId)
                .append(policy)
                .toHashCode();
    }    
    public static final class Builder {

        private final PolicyEvent object;

        public Builder() {
            object = new PolicyEvent();
        }

        public Builder withEventId(String value) {
            object.eventId = value;
            return this;
        }

        public Builder withEventTimestamp(Long value) {
            object.eventTimestamp = value;
            return this;
        }

        public Builder withEventType(EventType value) {
            object.eventType = value;
            return this;
        }

        public Builder withPolicyId(UUID value) {
            object.policyId = value;
            return this;
        }

        public Builder withPolicy(PolicyDto value) {
            object.policy = value;
            return this;
        }

        public PolicyEvent build() {
            return object;
        }

    }
}
