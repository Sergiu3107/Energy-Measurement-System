package ro.tuc.ds2024.dtos;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UserDetailsDTO {

    private UUID id;

    @NonNull
    @NotNull
    private String username;

    @NonNull
    @NotNull
    private String email;

    @NonNull
    @NotNull
    private String password;

    @NonNull
    @NotNull
    private String role;

    @NonNull
    @NotNull
    private String firstName;

    @NonNull
    @NotNull
    private String lastName;

}
