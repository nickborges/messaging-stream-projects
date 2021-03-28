package br.com.rabbitmq.producer.controller;

import br.com.rabbitmq.producer.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ProducerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void teste() throws Exception {
        LocalDate now = LocalDate.now();
        mockMvc.perform(post("/producer/topic/create")
                .content("Mensagem de hoje " + now))
                .andDo(print())
        .andExpect(status().isOk());
    }

}
