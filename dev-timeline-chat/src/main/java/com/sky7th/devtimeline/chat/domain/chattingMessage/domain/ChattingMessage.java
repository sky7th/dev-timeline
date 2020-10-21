package com.sky7th.devtimeline.chat.domain.chattingMessage.domain;

import com.sky7th.devtimeline.user.domain.User;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@NoArgsConstructor
@Getter
public class ChattingMessage {

    public enum MessageType {
        ENTER, TALK, QUIT, MULTIPLE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageId;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private Long roomId;

    private int userCount;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_chatting_message_user"))
    private User user;

    private String message;

    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public ChattingMessage(Long id, String messageId, MessageType type, Long roomId, int userCount,
        User user, String message, LocalDateTime createdDate) {
        this.id = id;
        this.messageId = messageId;
        this.type = type;
        this.roomId = roomId;
        this.userCount = userCount;
        this.user = user;
        this.message = message;
        this.createdDate = createdDate;
    }
}