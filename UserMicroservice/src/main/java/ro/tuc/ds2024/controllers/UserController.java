package ro.tuc.ds2024.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2024.dtos.*;
import ro.tuc.ds2024.entities.User;
import ro.tuc.ds2024.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/user")
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService; }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> dtos = userService.findUsers();
        for (UserDTO dto : dtos) {
            Link userLink = linkTo(methodOn(UserController.class)
                    .getUser(dto.getId())).withRel("userDetails");
            dto.add(userLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") UUID userId) {
        UserDTO dto = userService.findUserById(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /*@PostMapping(value = "/login")
    public ResponseEntity<LoginRespondData> login(@Valid @RequestBody LoginRequestData loginRequestData) {
        LoginRespondData user = userService.findUserByCredentials(loginRequestData.getUsername(), loginRequestData.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }*/

    @PostMapping(value = "/login")
    public ResponseEntity<JWTResponseData> authenticate(@Valid @RequestBody JWTRequestData jwtRequestData) {
        System.out.println("Authenticating...");
        JWTResponseData jwtResponseData = userService.login(jwtRequestData);
        return new ResponseEntity<>(jwtResponseData, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertUser(@Valid @RequestBody UserDetailsDTO userDTO, @RequestHeader("Authorization") String authorizationHeader) {
        UUID userID = userService.insert(userDTO, authorizationHeader);
        return new ResponseEntity<>(userID, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") UUID userId) {
        boolean removed = userService.delete(userId);
        return new ResponseEntity<>(removed, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDetailsDTO> updateUser(@PathVariable("id") UUID userId, @RequestBody User newUser) {
        UserDetailsDTO updatedUser = userService.update(userId, newUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    
}
