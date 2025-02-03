package ro.tuc.ds2024.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
public class UserAuth {

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String role;
}
