package ro.tuc.ds2024.services.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2024.entities.Consumption;
import ro.tuc.ds2024.entities.Device;
import ro.tuc.ds2024.repositories.ConsumptionRepository;
import ro.tuc.ds2024.repositories.DeviceRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RabbitMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    private final DeviceRepository deviceRepository;
    private final ConsumptionRepository consumptionRepository;
    private final NotifyService notifyService;

    private Integer lastHour = null;
    private double totalMeasurementValue = 0;
    private int measurementCount = 0;

    @Autowired
    public RabbitMQConsumer(DeviceRepository deviceRepository,
                            ConsumptionRepository consumptionRepository,
                            NotifyService notifyService) {
        this.deviceRepository = deviceRepository;
        this.notifyService = notifyService;
        this.consumptionRepository = consumptionRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.create.queue.name}"})
    public void consumeCreate(String message) {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        System.out.println(jsonObject);
        if (jsonObject.has("device_id") && !jsonObject.get("device_id").isJsonNull() &&
                jsonObject.has("user_id") && !jsonObject.get("user_id").isJsonNull() &&
                jsonObject.has("maxim_cost") && !jsonObject.get("maxim_cost").isJsonNull()) {

            try {
                UUID deviceId = UUID.fromString(jsonObject.get("device_id").getAsString());
                UUID userId = UUID.fromString(jsonObject.get("user_id").getAsString());
                Double maximCost = jsonObject.get("maxim_cost").getAsDouble();

                Device device = new Device(deviceId, userId, maximCost);
                deviceRepository.save(device);
            } catch (Exception e) {
                LOGGER.error("Error processing message: {}", message, e);
            }
        } else {
            LOGGER.warn("Message is missing required fields or contains null values: {}", message);
        }
    }

    @RabbitListener(queues = {"${rabbitmq.delete.queue.name}"})
    public void consumeDelete(String message) {
        UUID deviceId = UUID.fromString(message);

        if (!deviceRepository.existsById(deviceId)) {
            LOGGER.error("Device with ID {} does not exist", deviceId);
            return;
        }
        deviceRepository.deleteById(deviceId);
    }

    @RabbitListener(queues = {"${rabbitmq.simulator.queue.name}"}, concurrency = "2-10")
    public void consumeMeasurement(String message) {

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        long timestamp = jsonObject.get("timestamp").getAsLong();
        double measurementValue = jsonObject.get("measurement_value").getAsDouble();
        UUID deviceId = UUID.fromString(jsonObject.get("device_id").getAsString());

        Optional<Device> optionalDevice = deviceRepository.findById(deviceId);
        if (optionalDevice.isPresent()) {
            Device device = optionalDevice.get();

            ZonedDateTime dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.of("Europe/Bucharest"));
            int currentHour = dateTime.getHour();
            LocalDate day = dateTime.toLocalDate();

            if (lastHour == null || currentHour != lastHour) {
                if (lastHour != null) {
                    Double average = totalMeasurementValue / measurementCount;
                    Consumption consumption = new Consumption(day.toString(), lastHour, average, device);
                    if (average >= device.getMaximCost()) {
                        notifyService.sendNotification(device.getUserId());
                    }
                    consumptionRepository.save(consumption);
                }

                lastHour = currentHour;
                totalMeasurementValue = 0;
                measurementCount = 0;
            }

            totalMeasurementValue += measurementValue;
            measurementCount++;
        }
    }

}