package ro.tuc.ds2024.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2024.dtos.ConsumptionDTO;
import ro.tuc.ds2024.dtos.ConsumptionDetailsDTO;
import ro.tuc.ds2024.dtos.builders.ConsumptionBuilder;
import ro.tuc.ds2024.entities.Consumption;
import ro.tuc.ds2024.repositories.ConsumptionRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsumptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final ConsumptionRepository consumptionRepository;

    @Autowired
    public ConsumptionService(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    public List<ConsumptionDTO> findConsumptions() {
        List<Consumption> consumptionList = consumptionRepository.findAll();
        List<ConsumptionDTO> list = consumptionList.stream()
                .map(ConsumptionBuilder::toConsumptionDTO)
                .collect(Collectors.toList());
        for (ConsumptionDTO c : list
        ) {
            LOGGER.info(c.getHourlyConsumption() + " | " + c.getHour());
        }
        return list;
    }

    public List<ConsumptionDetailsDTO> findByDeviceAndDay(UUID deviceId, String day) {
        List<Consumption> consumptionList = consumptionRepository.findByDeviceIdAndDate(deviceId, day);
        return consumptionList.stream()
                .map(ConsumptionBuilder::toConsumptionDetailsDTO)
                .collect(Collectors.toList());
    }


}
