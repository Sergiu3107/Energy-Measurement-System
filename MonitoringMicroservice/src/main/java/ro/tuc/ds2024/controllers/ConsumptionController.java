package ro.tuc.ds2024.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2024.dtos.ConsumptionDTO;
import ro.tuc.ds2024.dtos.ConsumptionDetailsDTO;
import ro.tuc.ds2024.dtos.DeviceDTO;
import ro.tuc.ds2024.dtos.DeviceDetailsDTO;
import ro.tuc.ds2024.services.ConsumptionService;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/consumption")
public class ConsumptionController {

    private final ConsumptionService consumptionService;

    @Autowired
    public ConsumptionController(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @GetMapping()
    public ResponseEntity<List<ConsumptionDTO>> getConsumption() {
        List<ConsumptionDTO> dtos = consumptionService.findConsumptions();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/by-device-and-day")
    public ResponseEntity<List<ConsumptionDetailsDTO>> getConsumptionByDeviceAndDay(@RequestParam(name = "device_id") UUID deviceId, @RequestParam(name = "day") String day) {
        List<ConsumptionDetailsDTO> consumptionDetails = consumptionService.findByDeviceAndDay(deviceId, day);
        return new ResponseEntity<>(consumptionDetails, HttpStatus.OK);
    }

}
