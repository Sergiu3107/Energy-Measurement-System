package ro.tuc.ds2024.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2024.entities.Consumption;
import ro.tuc.ds2024.entities.Device;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class DeviceDTO extends RepresentationModel<DeviceDTO>{

    @NonNull
    private UUID id;

    @NonNull
    private UUID userId;

    @NonNull
    private Double maximCost;

}
