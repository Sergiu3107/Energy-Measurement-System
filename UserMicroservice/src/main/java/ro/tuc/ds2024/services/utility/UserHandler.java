package ro.tuc.ds2024.services.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service("utilityUserHandler")
public class UserHandler {

    private final RestTemplate restTemplate;
    private final String baseUrlRestUserOfDevice = "http://device-ms:8082/user";

    @Autowired
    public UserHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createPost(UUID id, String accessToken) {
        System.out.println("Add user to device");
        String postUrl = baseUrlRestUserOfDevice;

        Map<String, Object> request = new HashMap<>();
        request.put("id", id.toString());
        request.put("deviceList", new ArrayList<>());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        String res = restTemplate.postForObject(postUrl, entity, String.class);
        System.out.println(request);
        System.out.println(res);
        return res;
    }

    public void createDelete(UUID id) {

        String deleteUrl = baseUrlRestUserOfDevice + "/" + id;
        restTemplate.delete(deleteUrl);
    }
}
