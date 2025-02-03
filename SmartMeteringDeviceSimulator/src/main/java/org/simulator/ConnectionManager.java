package org.simulator;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionManager {
    private final String hostName;
    private final String username;
    private final String password;

    private Connection connection;
    private Channel channel;

    public ConnectionManager(String hostName, String username, String password) {
        this.hostName = hostName;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(hostName);
            factory.setUsername(username);
            factory.setPassword(password);

            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (Exception e) {
            throw new RuntimeException("Failed to establish RabbitMQ connection", e);
        }
    }

    public Channel getChannel() {
        if (channel == null) {
            throw new IllegalStateException("Channel is not initialized. Call connect() first.");
        }
        return channel;
    }

    public void close() {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            System.err.println("Error while closing RabbitMQ resources");
            e.printStackTrace();
        }
    }
}
