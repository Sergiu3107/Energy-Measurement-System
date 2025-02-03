package ro.tuc.ds2024.services.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.create.routing.key}")
    private String routingKeyCreate;

    @Value("${rabbitmq.delete.routing.key}")
    private String routingKeyDelete;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageCreate(String message){
        rabbitTemplate.convertAndSend(exchange, routingKeyCreate, message);
    }

    public void sendMessageDelete(String message){
        rabbitTemplate.convertAndSend(exchange, routingKeyDelete, message);
    }
}
