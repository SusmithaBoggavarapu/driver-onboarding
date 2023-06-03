package com.uber.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

@Slf4j
public class OnboardMessageErrorHandler implements CommonErrorHandler {

    @Override
    public void handleRecord(
            Exception thrownException,
            ConsumerRecord<?, ?> record,
            Consumer<?, ?> consumer,
            MessageListenerContainer container) {

        log.warn("global error handler for message: {}", record.value().toString());
        if (thrownException instanceof OnboardException) {
            log.error("Global error handler for message: {}", record.value().toString());
        } else {
            log.error("unknown error: {}", thrownException);
        }

        CommonErrorHandler.super.handleOtherException(thrownException, consumer, container, true);

    }
}