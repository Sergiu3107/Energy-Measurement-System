package ro.tuc.ds2024.dtos.builders;

import ro.tuc.ds2024.dtos.DeviceDTO;
import ro.tuc.ds2024.dtos.DeviceDetailsDTO;
import ro.tuc.ds2024.entities.Device;

public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getId(), device.getUserId(), device.getMaximCost());
    }

    public static DeviceDetailsDTO toDeviceDetailsDTO(Device device) {
        return new DeviceDetailsDTO(device.getId(), device.getUserId(), device.getMaximCost(), device.getConsumptionList());
    }
}
