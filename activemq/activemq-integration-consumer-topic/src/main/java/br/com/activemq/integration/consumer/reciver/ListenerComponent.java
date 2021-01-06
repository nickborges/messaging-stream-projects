package br.com.activemq.integration.consumer.reciver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerComponent {

    @JmsListener(id = "primeiroTopicoId",
                 destination = "${topic.primeiroTopico}",
                 containerFactory = "jmsFactoryTopic",
                 subscription = "primeiroTopico",
                 selector = "item is null OR item=true")
    public void onReceiverTopic(String str) {
        System.out.println(str);
    }

}