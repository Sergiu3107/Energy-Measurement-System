package ro.tuc.ds2024.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class IndexController {

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;
    @GetMapping(value = "/")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>("Device APP Service is running...", HttpStatus.OK);
    }
}
