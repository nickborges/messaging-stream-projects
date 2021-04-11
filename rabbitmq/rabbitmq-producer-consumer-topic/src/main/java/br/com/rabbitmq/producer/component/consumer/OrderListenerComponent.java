package br.com.rabbitmq.producer.component.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderListenerComponent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.order}")
    private String orderQueue;

    @RabbitListener(queues = "${queue.order}")
    public void consumer(String message){
        System.out.println("Queue: "+ orderQueue + " | Recived Message: " + message);
        //throw new RuntimeException("Erroooo!!!");
    }

}
