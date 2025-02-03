package ro.tuc.ds2024.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2024.dtos.DeviceDTO;
import ro.tuc.ds2024.dtos.DeviceDetailsDTO;
import ro.tuc.ds2024.entities.Device;
import ro.tuc.ds2024.services.DeviceService;

import javax.validation.Valid;
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
        for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDeviceById(dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDeviceById(@PathVariable("id") UUID deviceId) {
        DeviceDetailsDTO dto = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUserId(@PathVariable UUID userId) {
        List<DeviceDTO> devices = deviceService.findDevicesByUserId(userId);

        if (devices.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertDevice(@Valid @RequestBody DeviceDetailsDTO deviceDTO) {
        UUID deviceID = deviceService.insert(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteDevice(@PathVariable("id") UUID deviceId) {
        boolean removed = deviceService.delete(deviceId);
        return new ResponseEntity<>(removed, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DeviceDetailsDTO> updateDevice(@PathVariable("id") UUID deviceId, @RequestBody Device newDevice) {
        DeviceDetailsDTO updatedDevice = deviceService.update(deviceId, newDevice);
        return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @PutMapping(value = "/user/{uid}/add-device/{did}")
    public ResponseEntity<DeviceDTO> addDevice(@PathVariable("uid") UUID userId, @PathVariable("did") UUID deviceId) {
        DeviceDTO device = deviceService.addDeviceToUser(userId, deviceId);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }



}
