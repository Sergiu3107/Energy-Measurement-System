package org.simulator;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Simulator {
    private static final String QUEUE_NAME = "simulator_queue";
    private static final String EXCHANGE_NAME = "simulator_exchange";
    private static final String ROUTING_KEY = "simulator_routing_key";

    private static final String HOST_NAME = "localhost";
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    private ConnectionManager connectionManager;
    private MessageBroker broker;
    private Producer producer;
    private String deviceID;

    public Simulator(String deviceID) {
        this.connectionManager = new ConnectionManager(HOST_NAME, USERNAME, PASSWORD);
        this.deviceID = deviceID;
    }

    public void setup() {
        connectionManager.connect();

        broker = new MessageBroker(connectionManager.getChannel());

        broker.declareQueue(QUEUE_NAME);
        broker.declareExchange(EXCHANGE_NAME);
        broker.bindQueue(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        producer = new Producer(connectionManager.getChannel(), EXCHANGE_NAME, ROUTING_KEY);
    }

    public void run() {
        long baseTimestamp = System.currentTimeMillis();
        final long tenMinutesInMillis = 10 * 60 * 1000;
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        timeFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest"));

        try {
            List<Double> values = CSVService.readFromFile("src\\main\\resources\\sensor.csv");
            int counter = 0;
            for (Double value : values) {

                long simulatedTimestamp = baseTimestamp + (counter * tenMinutesInMillis);
                String simulatedTime = timeFormatter.format(new Date(simulatedTimestamp));
                String sendTime = timeFormatter.format(new Date());

                JSONObject data = new JSONObject();
                data.put("timestamp", simulatedTimestamp);
                data.put("device_id", this.deviceID);
                data.put("measurement_value", value);

                producer.sendMessage(data.toString());

                System.out.println("Sent value: " + value
                        + " | Simulated time: " + simulatedTime
                        + " | Sent at: " + sendTime);

                counter++;
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void shutdown() {
        connectionManager.close();
    }
}
