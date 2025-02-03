package ro.tuc.ds2024.dtos;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class DeviceDetailsDTO {

    private UUID id;

    @NonNull
    @NotNull
    private String description;

    @NonNull
    @NotNull
    private String address;

    @NonNull
    @NotNull
    private Double costPerHour;
}
