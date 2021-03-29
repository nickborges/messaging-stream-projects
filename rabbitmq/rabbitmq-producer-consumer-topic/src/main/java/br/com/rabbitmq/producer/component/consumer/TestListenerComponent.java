package br.com.rabbitmq.producer.component.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestListenerComponent {

    private static final String TESTE_QUEUE = "TesteQueue";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = TESTE_QUEUE)
    public void consumer(String message) throws Exception {
        throw new Exception("Erro DE TESTE");
    }

}
