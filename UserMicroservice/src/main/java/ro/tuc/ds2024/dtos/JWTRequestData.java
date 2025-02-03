package ro.tuc.ds2024.dtos;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JWTRequestData {

    private String username;

    private String password;
}
