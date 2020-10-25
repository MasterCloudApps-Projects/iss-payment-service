package es.urjc.code.payment.infrastructure.adapter.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PoliciesStreams {

	final String INPUT_POLICY_REGISTRED = "policy-registered-in";

	@Input(INPUT_POLICY_REGISTRED)
    SubscribableChannel inboundRegistred();

}
