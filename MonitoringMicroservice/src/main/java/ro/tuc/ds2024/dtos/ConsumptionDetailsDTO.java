package ro.tuc.ds2024.dtos;

import lombok.Data;
import lombok.NonNull;
import ro.tuc.ds2024.entities.Device;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ConsumptionDetailsDTO {

    private UUID id;

    @NonNull
    @NotNull
    private String day;

    @NonNull
    @NotNull
    private int hour;

    @NonNull
    @NotNull
    private Double hourlyConsumption;

    @NonNull
    @NotNull
    private UUID deviceId;
}
