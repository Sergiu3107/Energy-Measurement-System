package ro.tuc.ds2024.services.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotifyService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotifyService(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(UUID userId){
        String alert = "Device has surpassed the maxim consumption";
        String destination = "/topic/alert/" + userId;
        System.out.println(destination);
        messagingTemplate.convertAndSend(destination, alert);
    }


}
