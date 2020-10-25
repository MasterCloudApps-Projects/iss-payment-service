package es.urjc.code.payment.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

import es.urjc.code.payment.infrastructure.adapter.kafka.PoliciesStreams;

@EnableBinding(PoliciesStreams.class)
public class StreamsConfig {

}
