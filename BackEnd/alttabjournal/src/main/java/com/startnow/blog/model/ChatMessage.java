package com.startnow.blog.model;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private String sender;
    private String receiver;
    private String content;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return String.format("ChatMessage{sender='%s', receiver='%s', content='%s', timestamp=%s}",
                Objects.toString(sender, ""), Objects.toString(receiver, ""),
                content != null
                        ? (content.length() > 50 ? content.substring(0, 47) + "..." : content)
                        : "",
                Objects.toString(timestamp, "NO_TIMESTAMP"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(sender, that.sender) && Objects.equals(receiver, that.receiver)
                && Objects.equals(content, that.content)
                && Objects.equals(timestamp, that.timestamp);
    }
}
