package br.com.rabbitmq.producer.component.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EmailDLQComponent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${deadletter.queue.email}")
    private String queueDLQ;

    @RabbitListener(queues = "${deadletter.queue.email}")
    public void consumer(String message, Message message2) {
        Map<String, Object> headers = message2.getMessageProperties().getHeaders();
        List<Map<String, ?>> xDeath = (List<Map<String, ?>>) headers.get("x-death");
        String exchange = (String) xDeath.get(0).get("exchange");
        List<String> routingKeys = (List<String>) xDeath.get(0).get("routing-keys");
        String queue = (String) xDeath.get(0).get("queue");

        System.out.println("Queue: "+ queueDLQ + " | " +"Recived Message: " + message
                + " | " + "Origin exchange: " + exchange
                + " | " + "Origin route: " + routingKeys
                + " | " + "Origin queue: " + queue);

        System.out.println("Resending message to Queue: " + queue);
        rabbitTemplate.convertAndSend(queue, message);

    }
}
