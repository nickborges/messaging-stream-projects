package br.com.rabbitmq.producer.component.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProducerComponent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${exchange-topic.order}")
    private String orderExchange;

    @Value("${exchange-topic.route}")
    private String routeQueue;

    public void producer(String message){
        System.out.println("Sending message: " + message);
        rabbitTemplate.convertAndSend(orderExchange, routeQueue, message);
    }

}
