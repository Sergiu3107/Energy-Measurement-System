package ro.tuc.ds2024.dtos;

import lombok.Data;
import lombok.NonNull;
import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2024.entities.Device;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ConsumptionDTO extends RepresentationModel<ConsumptionDTO>{

    @NonNull
    private UUID id;

    @NonNull
    private String day;

    @NonNull
    private int hour;

    @NonNull
    private Double hourlyConsumption;

    @NonNull
    private DeviceDTO device;
}
