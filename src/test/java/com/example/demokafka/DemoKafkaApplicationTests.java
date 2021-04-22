package com.example.demokafka;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@EmbeddedKafka(topics = "someTopic", bootstrapServersProperty = "spring.kafka.bootstrap-servers")
class DemoKafkaApplicationTests {

  @Autowired
  private KafkaTemplate kafkaTemplate;

  @Autowired
  private DemoKafkaApplication.DemoMessageHandler handler;

  @Test
  void contextLoads() throws InterruptedException {
    {
      Message<String> message = MessageBuilder
          .withPayload("Hello World!")
          .setHeader("X-Track", UUID.randomUUID().toString())
          .build();
      kafkaTemplate.setDefaultTopic("someTopic");
      kafkaTemplate.send(message);
    }
    {
      Message<String> message = handler.messages.poll(5, TimeUnit.SECONDS);
      Assertions.assertThat(message.getPayload()).isEqualTo("Hello World!");
    }
  }

}
