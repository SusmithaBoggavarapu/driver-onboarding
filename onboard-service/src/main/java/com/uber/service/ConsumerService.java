package com.uber.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @KafkaListener(topics = "${onboarding.topic}", groupId = "onboard-driver")
    public void consume(String msg){
        log.info("received message {}", msg);
    }

}
