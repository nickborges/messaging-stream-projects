package br.com.rabbitmq.producer.component.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailListenerComponent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.email}")
    private String emailQueue;

    @RabbitListener(queues = "${queue.email}")
    public void consumer(String message){
        System.out.println("Queue: " + emailQueue + " | Recived Message: " + message);
        //throw new RuntimeException("Erro!!!");
    }

}
