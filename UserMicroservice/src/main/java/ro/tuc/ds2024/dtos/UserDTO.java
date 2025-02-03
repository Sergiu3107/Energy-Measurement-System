package ro.tuc.ds2024.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
public class UserDTO extends RepresentationModel<UserDTO> {

    @NonNull
    private UUID id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String role;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

}
