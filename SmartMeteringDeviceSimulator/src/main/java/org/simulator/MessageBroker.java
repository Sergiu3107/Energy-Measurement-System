package org.simulator;

import com.rabbitmq.client.Channel;

public class MessageBroker {
    private final Channel channel;

    public MessageBroker(Channel channel) {
        this.channel = channel;
    }

    public void declareQueue(String queueName) {
        try {
            channel.queueDeclare(queueName, false, false, false, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to declare queue: " + queueName, e);
        }
    }

    public void declareExchange(String exchangeName) {
        try {
            channel.exchangeDeclare(exchangeName, "direct");
        } catch (Exception e) {
            throw new RuntimeException("Failed to declare exchange: " + exchangeName, e);
        }
    }

    public void bindQueue(String queueName, String exchangeName, String routingKey) {
        try {
            channel.queueBind(queueName, exchangeName, routingKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to bind queue: " + queueName, e);
        }
    }
}
