package com.reactorkafka.consumer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.Properties;

@Configuration
public class AppConsumerConfg {

    public Properties getConsumerProperties(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"onet");
        properties.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        properties.put("use.latest.version", true);
        properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return properties;
    }

    @Bean
    public ReceiverOptions<String, SpecificRecord> kafkaReceiverOptions() {
        ReceiverOptions<String, SpecificRecord> basicReceiverOptions = ReceiverOptions.create(getConsumerProperties());
        System.out.println("receiver options bean created... "+ basicReceiverOptions.toString());
        return basicReceiverOptions.subscription(Collections.singletonList("test12345"));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, SpecificRecord> reactiveKafkaConsumerTemplate(ReceiverOptions<String, SpecificRecord> kafkaReceiverOptions) {
        System.out.println("reactive kafka template bean created...");
        return new ReactiveKafkaConsumerTemplate<String, SpecificRecord>(kafkaReceiverOptions);
    }
}
