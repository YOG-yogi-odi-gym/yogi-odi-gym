package com.health.yogiodigym.chat.config;

import com.health.yogiodigym.chat.dto.MessageDto.MessageResponseDto;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfiguration {

    private final Environment env;

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, MessageResponseDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageResponseDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, MessageResponseDto> consumerFactory() {
        JsonDeserializer<MessageResponseDto> deserializer = new JsonDeserializer<>();
        deserializer.addTrustedPackages("*");

        Map<String, Object> consumerConfigurations = new HashMap<>();
        consumerConfigurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.bootstrap-servers"));
        consumerConfigurations.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("spring.kafka.consumer.group-id"));
        consumerConfigurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfigurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        consumerConfigurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerConfigurations.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations, new StringDeserializer(), deserializer);
    }

}