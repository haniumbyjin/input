package com.conference.push.config.kafka;

import com.conference.push.model.response.Message;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

//    @Bean
//    public ProducerFactory<String, Message> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs(), null, new JsonSerializer<Message>());
//    }
//
//    @Bean
//    public KafkaTemplate<String, Message> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public Map<String, Object> producerConfigs() {
//
//        return ImmutableMap.<String, Object>builder()
//                .put("bootstrap.servers", "localhost:9092")
//                .put("key.serializer", IntegerSerializer.class)
//                .put("value.serializer", JsonSerializer.class)
//                .put("group.id", "spring-boot-test")
//                .build();
//    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();

        Map<String, Class<?>> classMap = new HashMap<>();
        classMap.put("b.event.Message", Message.class);
        typeMapper.setIdClassMapping(classMap);

        typeMapper.addTrustedPackages("*");
        JsonDeserializer<Message> deserializer = new JsonDeserializer<>(Message.class);
//        deserializer.addTrustedPackages("*");
        deserializer.setTypeMapper(typeMapper);
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), null, deserializer);
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        return ImmutableMap.<String, Object>builder()
                .put("bootstrap.servers", "localhost:9092")
                .put("key.deserializer", IntegerDeserializer.class)
                .put("value.deserializer", JsonDeserializer.class)
                .put("group.id", "spring-boot-test")
                .build();
    }
}
