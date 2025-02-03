package ro.tuc.ds2024.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequestData {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
