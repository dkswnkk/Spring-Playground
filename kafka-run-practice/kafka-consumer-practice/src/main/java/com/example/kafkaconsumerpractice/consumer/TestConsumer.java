package com.example.kafkaconsumerpractice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestConsumer {

    @KafkaListener(topics = "topic", groupId = "group_1")
    public void listener(Object data) {
        System.out.println(data);
    }
}
