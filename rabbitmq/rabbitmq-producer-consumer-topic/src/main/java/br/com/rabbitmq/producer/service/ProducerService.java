package br.com.rabbitmq.producer.service;

import br.com.rabbitmq.producer.component.producer.ProducerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private ProducerComponent component;

    public void producer(String message){
        component.producer(message);
    }

}
