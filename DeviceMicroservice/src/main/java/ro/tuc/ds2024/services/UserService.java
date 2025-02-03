package ro.tuc.ds2024.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2024.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2024.dtos.UserDTO;
import ro.tuc.ds2024.dtos.UserDetailsDTO;
import ro.tuc.ds2024.dtos.builders.UserBuilder;
import ro.tuc.ds2024.entities.Device;
import ro.tuc.ds2024.entities.User;
import ro.tuc.ds2024.repositories.DeviceRepository;
import ro.tuc.ds2024.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public UserService(UserRepository userRepository, DeviceRepository deviceRepository) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;

    }

    public List<UserDTO> findUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDTO(prosumerOptional.get());
    }

    public UUID insert(UserDetailsDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user = userRepository.save(user);

        LOGGER.debug("User with id {} was inserted in db", user.getId());
        return user.getId();
    }

    public boolean delete(UUID id) {
        Optional<User> user;
        if (!userRepository.existsById(id)) {
            LOGGER.error("User with id {} was not found in db", id);
            return false;
        }

        user = userRepository.findById(id);
        if(user.isPresent()){
            List<Device> deviceList = user.get().getDeviceList();
            for (Device device: deviceList) {
                device.setUser(null);
                deviceRepository.save(device);
            }
        }

        userRepository.deleteById(id);
        return true;
    }

}
