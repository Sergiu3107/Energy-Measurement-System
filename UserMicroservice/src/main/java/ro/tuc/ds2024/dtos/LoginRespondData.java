package ro.tuc.ds2024.dtos;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class LoginRespondData {

    @NonNull
    private UUID id;

    @NonNull
    private String username;

    @NonNull
    private String role;
}
