package com.reactorkafka.consumer;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ReactiveConsumerService implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(ReactiveConsumerService.class);

    private final ReactiveKafkaConsumerTemplate<String, SpecificRecord> reactiveKafkaConsumerTemplate;

    public ReactiveConsumerService(ReactiveKafkaConsumerTemplate<String, SpecificRecord> reactiveKafkaConsumerTemplate) {
        this.reactiveKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
    }

    private Flux<ConsumerRecord<String, SpecificRecord>> consumeFakeConsumerDTO() {
        return reactiveKafkaConsumerTemplate.receiveAutoAck()
                .doOnNext(consumerRecord -> {
                    log.info(consumerRecord.value().toString());
                });
    }

    @Override
    public void run(String... args) {
        // we have to trigger consumption
        consumeFakeConsumerDTO().subscribe();
    }
}
