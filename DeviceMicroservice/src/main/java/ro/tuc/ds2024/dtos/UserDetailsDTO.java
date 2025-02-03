package ro.tuc.ds2024.dtos;

import lombok.Data;
import lombok.NonNull;
import ro.tuc.ds2024.entities.Device;

import java.util.List;
import java.util.UUID;

@Data
public class UserDetailsDTO {

    @NonNull
    private UUID id;

    @NonNull
    private List<Device> deviceList;



}
