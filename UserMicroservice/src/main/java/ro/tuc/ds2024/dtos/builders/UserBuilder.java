package ro.tuc.ds2024.dtos.builders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.tuc.ds2024.dtos.LoginRespondData;
import ro.tuc.ds2024.dtos.UserDetailsDTO;
import ro.tuc.ds2024.dtos.UserDTO;
import ro.tuc.ds2024.entities.User;
public class UserBuilder {
    private UserBuilder() {
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(), user.getFirstName(), user.getLastName());
    }

    public static UserDetailsDTO toUserDetailsDTO(User user) {
        return new UserDetailsDTO(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(), user.getFirstName(), user.getLastName());
    }

    public static LoginRespondData toLoginRespondData(User user) {
        return new LoginRespondData(user.getId(), user.getUsername(), user.getRole());
    }

    public static User toEntity(UserDetailsDTO userDetailsDTO) {
        return new User(userDetailsDTO.getUsername(), userDetailsDTO.getEmail(), userDetailsDTO.getPassword(), userDetailsDTO.getRole(), userDetailsDTO.getFirstName(), userDetailsDTO.getLastName());
    }
}
