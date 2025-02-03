package ro.tuc.ds2024.dtos.builders;

import ro.tuc.ds2024.dtos.DeviceDTO;
import ro.tuc.ds2024.dtos.DeviceDetailsDTO;
import ro.tuc.ds2024.dtos.ConsumptionDTO;
import ro.tuc.ds2024.dtos.ConsumptionDetailsDTO;
import ro.tuc.ds2024.entities.Device;
import ro.tuc.ds2024.entities.Consumption;

public class ConsumptionBuilder {

    private ConsumptionBuilder() {
    }

    public static ConsumptionDTO toConsumptionDTO(Consumption consumption) {
        return new ConsumptionDTO(
                consumption.getId(),
                consumption.getDay(),
                consumption.getHour(),
                consumption.getHourlyConsumption(),
                DeviceBuilder.toDeviceDTO(consumption.getDevice()));
    }

    public static ConsumptionDetailsDTO toConsumptionDetailsDTO(Consumption consumption) {
        return new ConsumptionDetailsDTO(
                consumption.getDay(),
                consumption.getHour(),
                consumption.getHourlyConsumption(),
                consumption.getDevice().getUserId());
    }
}
