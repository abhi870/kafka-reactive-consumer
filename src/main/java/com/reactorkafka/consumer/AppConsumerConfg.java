package com.reactorkafka.consumer;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Configuration
public class AppConsumerConfg {

    @Bean
    public ReceiverOptions<String, String> kafkaReceiverOptions(KafkaProperties kafkaProperties) {
        ReceiverOptions<String, String> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        System.out.println("receiver options bean created... "+ basicReceiverOptions.toString());
        return basicReceiverOptions.subscription(Collections.singletonList("test12345"));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate(ReceiverOptions<String, String> kafkaReceiverOptions) {
        System.out.println("reactive kafka template bean created...");
        return new ReactiveKafkaConsumerTemplate<String, String>(kafkaReceiverOptions);
    }
}
