package ro.tuc.ds2024.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuc.ds2024.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2024.dtos.*;
import ro.tuc.ds2024.dtos.builders.UserBuilder;
import ro.tuc.ds2024.entities.User;
import ro.tuc.ds2024.repositories.UserRepository;
import ro.tuc.ds2024.services.utility.JwtUtil;
import ro.tuc.ds2024.services.utility.UserHandler;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserHandler userHandler;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, UserHandler userHandler, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userHandler = userHandler;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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

    public LoginRespondData findUserByCredentials(String username, String password) {
        Optional<User> prosumerOptional = userRepository.findByUsername(username);
        if (username != null && !prosumerOptional.isPresent()) {
            LOGGER.error("Login: User with id {} was not found in db", username);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with username: " + username);
        }
        if (password != null && !password.equals(prosumerOptional.get().getPassword())) {
            LOGGER.error("Wrong password for user {}", username);
            throw new ResourceNotFoundException("Wrong password for user " + username + " \n " + password + " | " + prosumerOptional.get().getPassword());
        }
        return UserBuilder.toLoginRespondData(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(UserDetailsDTO userDTO, String header) {
        User user = UserBuilder.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        String response = userHandler.createPost(user.getId(), header);

        LOGGER.debug("User with id {} was inserted in db", user.getId());
        LOGGER.debug("User to Device info: {}", response);
        return user.getId();
    }

    @Transactional
    public boolean delete(UUID id) {
        if (!userRepository.existsById(id)) {
            LOGGER.error("User with id {} was not found in db", id);
            return false;
        }
        userRepository.deleteById(id);
        userHandler.createDelete(id);
        return true;
    }

    public UserDetailsDTO update(UUID id, User newUser) {
        if (!userRepository.existsById(id)) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }

        User currentUser = userRepository.findById(id).orElse(null);
        if (currentUser != null) {
            currentUser.setUsername(newUser.getUsername());
            currentUser.setEmail(newUser.getEmail());
            currentUser.setPassword(newUser.getPassword());
            currentUser.setRole(newUser.getRole());
            currentUser.setFirstName(newUser.getFirstName());
            currentUser.setLastName(newUser.getLastName());

            User updatedUser = userRepository.save(currentUser);
            LOGGER.debug("User with id {} was updated in db", updatedUser.getId());
            return UserBuilder.toUserDetailsDTO(updatedUser);
        } else {
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
    }

    @PostConstruct
    private void initAdmin() {
        Optional<User> optionalAdmin = userRepository.findByEmail("admin@email.com");

        if (!optionalAdmin.isPresent()) {
            UserDetailsDTO admin = new UserDetailsDTO("admin", "admin@email.com", "123", "ADMIN", "John", "Cena");
            admin.setPassword(passwordEncoder.encode("123"));
            User user = UserBuilder.toEntity(admin);
            userRepository.save(user);
        }
    }

    private User findUserByAuth(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (username != null && !userOpt.isPresent()) {
            System.out.println("USER NOT FOUND");
            throw new ResourceNotFoundException("User not found");
        }
        if (password != null && !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            System.out.println("WRONG PASSWORD");

            System.out.println(userOpt.get().getPassword());
            throw new ResourceNotFoundException("Wrong password");
        }
        return userOpt.get();
    }

    public JWTResponseData login(JWTRequestData jwtRequestData) {
        User user = findUserByAuth(jwtRequestData.getUsername(), jwtRequestData.getPassword());
        System.out.println(user.getFirstName());

        UserAuth userAuth = new UserAuth(user.getUsername(), user.getPassword(), user.getRole());

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userAuth, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtil.generateAccessToken(
                user.getUsername(),
                user.getRole(),
                user.getId().toString()
        );

        JWTResponseData jwtResponseData = new JWTResponseData();
        jwtResponseData.setAccessToken(accessToken);

        return jwtResponseData;
    }
}
