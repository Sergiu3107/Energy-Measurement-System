package ro.tuc.ds2024.dtos;

import lombok.Data;
import lombok.NonNull;
import ro.tuc.ds2024.entities.Consumption;

import java.util.List;
import java.util.UUID;

@Data
public class DeviceDetailsDTO {

    @NonNull
    private UUID id;

    @NonNull
    private UUID userId;

    @NonNull
    private Double maximCost;

    @NonNull
    private List<Consumption> consumptionList;
}
