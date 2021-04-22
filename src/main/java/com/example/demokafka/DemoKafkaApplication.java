package com.example.demokafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SpringBootApplication
public class DemoKafkaApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoKafkaApplication.class, args);
  }

  @Component
  public static class DemoMessageHandler {

    final BlockingQueue<Message<String>> messages = new LinkedBlockingQueue<>();

    @KafkaListener(topics = "someTopic")
    public void processMessage(Message<String> message) {
      System.out.println("HEADERS:️" + message.getHeaders());
      System.out.println("PAYLOAD:️" + message.getPayload());
      messages.add(message);
    }

  }

}
