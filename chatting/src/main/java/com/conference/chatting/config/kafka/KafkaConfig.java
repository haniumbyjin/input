package com.conference.chatting.config.kafka;


import com.conference.chatting.model.Message;
import com.google.common.collect.ImmutableMap;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, Message> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), null, new JsonSerializer<Message>());
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public Map<String, Object> producerConfigs() {

        return ImmutableMap.<String, Object>builder()
                .put("bootstrap.servers", "localhost:9092")
                .put("key.serializer", IntegerSerializer.class)
                .put("value.serializer", JsonSerializer.class)
                .put("group.id", "spring-boot-test")
                .build();
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, Message> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), null, new JsonDeserializer<>(Message.class));
//    }
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        return ImmutableMap.<String, Object>builder()
//                .put("bootstrap.servers", "localhost:9092")
//                .put("key.deserializer", IntegerDeserializer.class)
//                .put("value.deserializer", JsonDeserializer.class)
//                .put("group.id", "spring-boot-test")
//                .build();
//    }
}

