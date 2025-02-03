package ro.tuc.ds2024.services;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2024.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2024.dtos.DeviceDTO;
import ro.tuc.ds2024.dtos.DeviceDetailsDTO;
import ro.tuc.ds2024.dtos.builders.DeviceBuilder;
import ro.tuc.ds2024.entities.Device;
import ro.tuc.ds2024.entities.User;
import ro.tuc.ds2024.repositories.DeviceRepository;
import ro.tuc.ds2024.repositories.UserRepository;
import ro.tuc.ds2024.services.utility.RabbitMQProducer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final RabbitMQProducer mqProducer;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UserRepository userRepository, RabbitMQProducer mqProducer) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.mqProducer = mqProducer;
    }

    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDetailsDTO findDeviceById(UUID id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailsDTO(prosumerOptional.get());
    }

    public List<DeviceDTO> findDevicesByUserId(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            LOGGER.error("User with id {} was not found in db", userId);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + userId);
        }
        return user.get().getDeviceList().stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO addDeviceToUser(UUID userId, UUID deviceId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);

        if (userOpt.isPresent() && deviceOpt.isPresent()) {
            User user = userOpt.get();
            Device device = deviceOpt.get();

            device.setUser(user);

            if (!user.getDeviceList().contains(device)) {
                user.getDeviceList().add(device);
            }

            deviceRepository.save(device);

            JSONObject data = new JSONObject();
            data.put("device_id", device.getId().toString());
            data.put("maxim_cost", device.getCostPerHour());
            data.put("user_id", device.getUser().getId().toString());
            mqProducer.sendMessageCreate(data.toString());

            return DeviceBuilder.toDeviceDTO(device);
        } else {
            if (!userOpt.isPresent()) {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            } else {
                throw new IllegalArgumentException("Device not found with ID: " + deviceId);
            }
        }
    }

    public UUID insert(DeviceDetailsDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        return device.getId();
    }

    public boolean delete(UUID id) {
        if (!deviceRepository.existsById(id)) {
            LOGGER.error("Device with id {} was not found in db", id);
            return false;
        }
        mqProducer.sendMessageDelete(id.toString());
        deviceRepository.deleteById(id);
        return true;
    }

    public DeviceDetailsDTO update(UUID id, Device newDevice) {
        if (!deviceRepository.existsById(id)) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }

        Device currentDevice = deviceRepository.findById(id).orElse(null);
        if (currentDevice != null) {
            currentDevice.setDescription(newDevice.getDescription());
            currentDevice.setAddress(newDevice.getAddress());
            currentDevice.setCostPerHour(newDevice.getCostPerHour());

            Device updatedDevice = deviceRepository.save(currentDevice);

            JSONObject data = new JSONObject();
            data.put("device_id", updatedDevice.getId().toString());
            data.put("maxim_cost", updatedDevice.getCostPerHour());
            data.put("user_id", updatedDevice.getUser().getId().toString());
            mqProducer.sendMessageCreate(data.toString());

            LOGGER.debug("Device with id {} was updated in db", updatedDevice.getId());
            return DeviceBuilder.toDeviceDetailsDTO(updatedDevice);
        } else {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
    }

}
