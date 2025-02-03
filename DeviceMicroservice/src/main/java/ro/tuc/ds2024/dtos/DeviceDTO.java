package ro.tuc.ds2024.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
public class DeviceDTO extends RepresentationModel<DeviceDTO>{

    @NonNull
    private UUID id;

    @NonNull
    private String description;

    @NonNull
    private String address;

    @NonNull
    private Double costPerHour;

    @NonNull
    private UserDTO user;
}
