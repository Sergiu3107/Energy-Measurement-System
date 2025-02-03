package ro.tuc.ds2024.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.create.queue.name}")
    private String queueCreate;

    @Value("${rabbitmq.create.routing.key}")
    private String routingKeyCreate;

    @Value("${rabbitmq.delete.queue.name}")
    private String queueDelete;

    @Value("${rabbitmq.delete.routing.key}")
    private String routingKeyDelete;

    @Bean
    public TopicExchange exchange(){
        System.out.println("Creating exchange: " + exchange);
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue queueCreate(){
        System.out.println("Creating queue: " + queueCreate);
        return new Queue(queueCreate, true);
    }

    @Bean
    public Queue queueDelete(){
        System.out.println("Creating queue: " + queueDelete);
        return new Queue(queueDelete, true);
    }

    @Bean
    public Binding bindingCreate(){
        System.out.println("Creating binding for queue: " + queueCreate + " with exchange: " + exchange);
        return BindingBuilder
                .bind(queueCreate())
                .to(exchange())
                .with(routingKeyCreate);
    }

    @Bean
    public Binding bindingDelete(){
        System.out.println("Creating binding for queue: " + queueDelete + " with exchange: " + exchange);
        return BindingBuilder
                .bind(queueDelete())
                .to(exchange())
                .with(routingKeyDelete);
    }

    
}
