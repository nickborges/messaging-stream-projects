package br.com.rabbitmq.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${exchange-topic.order}")
    private String topicExchange;

    @Value("${exchange-topic.route}")
    private String routeTopicExchange;

    @Value("${deadletter.exchange}")
    private String directExchangeDLQ;

    @Value("${deadletter.route.order}")
    private String orderRouteDLQ;

    @Value("${deadletter.route.email}")
    private String emailRouteDLQ;

    @Value("${deadletter.queue.order}")
    private String orderQueueDLQ;

    @Value("${deadletter.queue.email}")
    private String emailQueueDLQ;

    @Value("${queue.order}")
    private String orderQueue;

    @Value("${queue.email}")
    private String emailQueue;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        System.out.println("Connect RabbitMQ in host: " + host);
        return new CachingConnectionFactory(host);
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    //----  EXCHANGES ----

    @Bean("topicExchange")
    @Qualifier(value = "topicExchange")
    TopicExchange topicExchange(){
        return new TopicExchange(topicExchange);
    }

    @Bean("directExchangeDLQ")
    @Qualifier(value = "directExchangeDLQ")
    DirectExchange directExchangeDLQ(){
        return new DirectExchange(directExchangeDLQ);
    }

    //----  ORDER ----

    @Bean("orderQueue")
    @Qualifier(value = "orderQueue")
    Queue orderQueue(){
        Queue q = new Queue(orderQueue, true);
        q.addArgument("x-queue-type", "classic");
        q.addArgument("x-dead-letter-exchange", directExchangeDLQ);
        q.addArgument("x-dead-letter-routing-key", orderRouteDLQ);
        return q;
    }

    @Bean("orderBinding")
    @Qualifier(value = "orderBinding")
    Binding orderBinding(@Qualifier(value = "orderQueue") Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(routeTopicExchange);
    }

    @Bean("orderQueueDLQ")
    @Qualifier(value = "orderQueueDLQ")
    Queue orderQueueDLQ(){
        Queue q = new Queue(orderQueueDLQ, true);
        q.addArgument("x-queue-type", "classic");
        return q;
    }

    @Bean("orderQueueDLQBinding")
    @Qualifier(value = "orderQueueDLQBinding")
    Binding orderQueueDLQBinding(@Qualifier(value = "orderQueueDLQ") Queue queue,
                                 @Qualifier(value = "directExchangeDLQ") DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(orderRouteDLQ);
    }

    //----  EMAIL ----

    @Bean("emailQueue")
    @Qualifier(value = "emailQueue")
    Queue emailQueue(){
        Queue q = new Queue(emailQueue, true);
        q.addArgument("x-queue-type", "classic");
        q.addArgument("x-dead-letter-exchange", directExchangeDLQ);
        q.addArgument("x-dead-letter-routing-key", emailRouteDLQ);
        return q;
    }

    @Bean("emailBinding")
    @Qualifier(value = "emailBinding")
    Binding emailBinding(@Qualifier(value = "emailQueue") Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(routeTopicExchange);
    }

    @Bean("emailQueueDLQ")
    @Qualifier(value = "emailQueueDLQ")
    Queue emailQueueDLQ(){
        Queue q = new Queue(emailQueueDLQ, true);
        q.addArgument("x-queue-type", "classic");
        return q;
    }

    @Bean("emailQueueDLQBinding")
    @Qualifier(value = "emailQueueDLQBinding")
    Binding emailQueueDLQBinding(@Qualifier(value = "emailQueueDLQ") Queue queue,
                                 @Qualifier(value = "directExchangeDLQ") DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(emailRouteDLQ);

    }

    //-----------------------

}
