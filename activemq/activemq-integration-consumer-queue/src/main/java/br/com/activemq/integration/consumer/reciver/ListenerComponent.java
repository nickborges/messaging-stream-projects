package br.com.activemq.integration.consumer.reciver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerComponent {

    @JmsListener(destination = "${queue.primeiraFila}")
    public void onReceiverQueue(String str) {
        System.out.println(str);
    }

}