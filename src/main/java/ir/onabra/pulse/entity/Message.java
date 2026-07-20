package ir.onabra.pulse.entity;


import ir.onabra.pulse.base.BaseAuditableEntity;
import ir.onabra.pulse.enums.MessageType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "messages",
        indexes = {
                @Index(
                        name = "idx_messages_chat_sent_at",
                        columnList = "chat_id, sent_at"
                ),
                @Index(
                        name = "idx_messages_sender_user_id",
                        columnList = "sender_user_id"
                ),
                @Index(
                        name = "idx_messages_reply_to_message_id",
                        columnList = "reply_to_message_id"
                ),
                @Index(
                        name = "idx_messages_deleted_at",
                        columnList = "deleted_at"
                )
        }
)
public class Message extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "chat_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_messages_chat"
            )
    )
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sender_user_id",
            foreignKey = @ForeignKey(
                    name = "fk_messages_sender_user"
            )
    )
    private UserEntity senderUser;

    @Column(
            name = "content",
            columnDefinition = "text"
    )
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "message_type",
            nullable = false,
            length = 30
    )
    private MessageType messageType = MessageType.TEXT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "reply_to_message_id",
            foreignKey = @ForeignKey(
                    name = "fk_messages_reply_to_message"
            )
    )
    private Message replyToMessage;

    @Column(name = "edited_at")
    private Instant editedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(
            name = "sent_at",
            nullable = false,
            updatable = false
    )
    private Instant sentAt;

    @PrePersist
    public void prePersist() {
        if (sentAt == null) {
            sentAt = Instant.now();
        }
    }

    public boolean isEdited() {
        return editedAt != null;
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}

