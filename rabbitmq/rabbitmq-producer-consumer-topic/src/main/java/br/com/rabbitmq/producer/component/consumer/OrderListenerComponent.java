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

    private static final String ORDER_QUEUE = "OrderQueue";

    @RabbitListener(queues = ORDER_QUEUE)
    public void consumer(String message){
        System.out.println("Queue: "+ ORDER_QUEUE + " | Recived Message: " + message);
    }

}
