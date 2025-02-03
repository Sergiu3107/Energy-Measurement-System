package ro.tuc.ds2024.dtos.builders;

import ro.tuc.ds2024.dtos.UserDetailsDTO;
import ro.tuc.ds2024.dtos.UserDTO;
import ro.tuc.ds2024.entities.User;

public class UserBuilder {

    private UserBuilder() {
    }

    public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId());
    }

    public static UserDetailsDTO toUserDetailsDTO(User user) {
        return new UserDetailsDTO(user.getId(), user.getDeviceList());
    }

    public static User toEntity(UserDetailsDTO userDetailsDTO) {
        return new User(userDetailsDTO.getId(), userDetailsDTO.getDeviceList());
    }
}
