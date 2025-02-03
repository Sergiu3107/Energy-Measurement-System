package ro.tuc.ds2024.controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ro.tuc.ds2024.dtos.builders.Message;

@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @MessageMapping("/send/room/{roomId}")
    @SendTo("/topic/room/{roomId}/send")
    public Message sendMessage(@Payload Message message, @DestinationVariable String roomId) {
        return message;
    }

    @MessageMapping("/typing/room/{roomId}")
    @SendTo("/topic/room/{roomId}/typing")
    public String typingMessage(@Payload String username, @DestinationVariable String roomId) {
        return username;
    }

//    @MessageMapping("/enter/room/{roomId}")
//    @SendTo("/topic/room/{roomId}/enter")
//    public String userEntered(@Payload String username, @DestinationVariable String roomId) { return username; }
//
//    @MessageMapping("/seen/room/{roomId}")
//    @SendTo("/topic/room/{roomId}/seen")
//    public String seenMessage(@Payload String username, @DestinationVariable String roomId) { return username; }
}
