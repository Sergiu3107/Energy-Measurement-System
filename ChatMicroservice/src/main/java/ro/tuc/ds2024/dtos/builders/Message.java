package ro.tuc.ds2024.dtos.builders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @JsonProperty("content")
    private String content;

    @JsonProperty("sender")
    private User sender;

    @JsonProperty("type")
    private MessageType type;

    public enum MessageType {
        TYPING,
        DELIVERED,
        SEEN
    }

    // Assuming you have a User class (sender) with necessary fields
    @Getter
    @Setter
    public static class User {

        @JsonProperty("id")
        private String id;

        @JsonProperty("username")
        private String username;

        @JsonProperty("color")
        private String color;
    }


}
