package ro.tuc.ds2024.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> {

    @NonNull
    private UUID id;

}
