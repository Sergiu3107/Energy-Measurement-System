package ro.tuc.ds2024.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2024.dtos.DeviceDTO;
import ro.tuc.ds2024.dtos.DeviceDetailsDTO;
import ro.tuc.ds2024.services.DeviceService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {this.deviceService = deviceService; }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.findDevices();
//        for (DeviceDTO dto : dtos) {
//            Link deviceLink = linkTo(methodOn(DeviceController.class)
//                    .getDeviceById(dto.getId())).withRel("deviceDetails");
//            dto.add(deviceLink);
//        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

//    @GetMapping(value = "/{id}")
//    public ResponseEntity<DeviceDetailsDTO> getDeviceById(@PathVariable("id") UUID deviceId) {
//        DeviceDetailsDTO dto = deviceService.findDeviceById(deviceId);
//        return new ResponseEntity<>(dto, HttpStatus.OK);
//    }



}
