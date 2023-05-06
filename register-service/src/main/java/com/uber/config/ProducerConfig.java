package com.uber.config;

import com.uber.config.PropertyConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ProducerConfig {

    @Autowired
    private PropertyConfig propertyConfig;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(propertyConfig.getOnboardingTopic()).build();
    }
}
