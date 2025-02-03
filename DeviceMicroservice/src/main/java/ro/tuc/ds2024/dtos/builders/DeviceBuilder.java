package ro.tuc.ds2024.dtos.builders;

import ro.tuc.ds2024.dtos.*;
import ro.tuc.ds2024.dtos.DeviceDTO;
import ro.tuc.ds2024.dtos.DeviceDetailsDTO;
import ro.tuc.ds2024.entities.Device;

public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(
                device.getId(),
                device.getDescription(),
                device.getAddress(),
                device.getCostPerHour(),
                device.getUser() != null ? UserBuilder.toUserDTO(device.getUser()) : new UserDTO());
    }

    public static DeviceDetailsDTO toDeviceDetailsDTO(Device device) {
        return new DeviceDetailsDTO(device.getDescription(), device.getAddress(), device.getCostPerHour());
    }

    public static Device toEntity(DeviceDetailsDTO deviceDetailsDTO) {
        return new Device(deviceDetailsDTO.getDescription(), deviceDetailsDTO.getAddress(), deviceDetailsDTO.getCostPerHour());
    }
}
