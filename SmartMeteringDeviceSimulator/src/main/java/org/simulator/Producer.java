package org.simulator;

import com.rabbitmq.client.Channel;

public class Producer {
    private final Channel channel;
    private final String exchangeName;
    private final String routingKey;

    public Producer(Channel channel, String exchangeName, String routingKey) {
        this.channel = channel;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public void sendMessage(String message) {
        try {
            channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message: " + message, e);
        }
    }
}
