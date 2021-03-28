package br.com.rabbitmq.producer.controller;

import br.com.rabbitmq.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/producer/topic")
public class ProducerController {

    @Autowired
    private ProducerService service;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> producer(@RequestBody String message){
        service.producer(message);
        return ResponseEntity.ok("message send success.");
    }
}
