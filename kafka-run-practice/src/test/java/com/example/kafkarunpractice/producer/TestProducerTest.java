package com.example.kafkarunpractice.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestProducerTest {

    @Autowired
    private TestProducer testProducer;

    @Test
    void test() {
        testProducer.create();
    }
}