package br.com.activemq.integration.producer.producer;

import javax.jms.DeliveryMode;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerComponent {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${queue.primeiraFila}")
    private String queue;

    @Value("${topic.primeiroTopico}")
    private String topic;

    public void runQueue(String msg){
        jmsTemplate.convertAndSend(queue, msg);
    }

    public void runTopic(String msg){
        jmsTemplate.convertAndSend(new ActiveMQTopic(topic), msg);
    }
    
	public void runTopiSelector(String msg, boolean ativo) {
		jmsTemplate.convertAndSend(new ActiveMQTopic(topic), msg, messagePostProcessor -> {
			messagePostProcessor.setBooleanProperty("item", ativo);				//define o atrivo e o valor que o consumidor deve filtrar
			//messagePostProcessor.setJMSDeliveryMode(DeliveryMode.PERSISTENT); //define se é para o ActiveMQ salvar as mensagens no banco dele, caso ele caía ao subir novamente as mensagens estão salvas
			//messagePostProcessor.setJMSPriority(9);							//define a prioridade da mensagem na fila, quanto maior o número mais alta a prioridade de 0 até 9
			//messagePostProcessor.setJMSDeliveryTime(5000);					//define o tempo de vida de mensagem em milesegundos
			
			return messagePostProcessor;
		});
	}

}
