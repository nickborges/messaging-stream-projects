package br.com.rabbitmq.producer.component.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailListenerComponent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String EMAIL_QUEUE = "EmailQueue";

    @RabbitListener(queues = EMAIL_QUEUE)
    public void consumer(String message){
        System.out.println("Queue: "+ EMAIL_QUEUE + " | Recived Message: " + message);
    }

}
