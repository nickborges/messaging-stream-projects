package br.com.activemq.integration.producer.controller;

import br.com.activemq.integration.producer.producer.ProducerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ActiveMqProducerController {

    @Autowired
    ProducerComponent producerComponent;

    @PostMapping("/queue/producer")
    public ResponseEntity execute(@RequestBody String mensagem){
        producerComponent.runQueue(mensagem);
        return ResponseEntity.ok("Mensagem : " + mensagem + " envida com suceso!");
    }

    @PostMapping("/topic/producer")
    public ResponseEntity topicExecute(@RequestBody String mensagem){
        producerComponent.runTopic(mensagem);
        return ResponseEntity.ok("Tópico : " + mensagem + " envida com suceso!");
    }
    
    @PostMapping("/topic/producer/{ativo}")
    public ResponseEntity topicExecute(@PathVariable boolean ativo, @RequestBody String mensagem){
        producerComponent.runTopiSelector(mensagem, ativo);
        return ResponseEntity.ok("Tópico : " + mensagem + " envida com suceso!");
    }

}
