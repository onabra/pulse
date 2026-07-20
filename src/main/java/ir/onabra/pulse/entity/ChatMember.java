package ir.onabra.pulse.entity;

import ir.onabra.pulse.base.BaseUuidEntity;
import ir.onabra.pulse.enums.ChatMemberRole;
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
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "chat_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_chat_members_chat_user",
                        columnNames = {
                                "chat_id",
                                "user_id"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_chat_members_chat_id",
                        columnList = "chat_id"
                ),
                @Index(
                        name = "idx_chat_members_user_id",
                        columnList = "user_id"
                ),
                @Index(
                        name = "idx_chat_members_chat_role",
                        columnList = "chat_id, role"
                )
        }
)
public class ChatMember extends BaseUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "chat_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_chat_members_chat"
            )
    )
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_chat_members_user"
            )
    )
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "role",
            nullable = false,
            length = 20
    )
    private ChatMemberRole role = ChatMemberRole.MEMBER;

    @Column(
            name = "joined_at",
            nullable = false,
            updatable = false
    )
    private Instant joinedAt;

    @Column(name = "is_muted", nullable = false)
    private boolean isMuted = false;

    @Column(name = "is_pinned", nullable = false)
    private boolean isPinned = false;

    @Column(name = "is_archived", nullable = false)
    private boolean isArchived = false;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked = false;

    @Column(name = "is_typing", nullable = false)
    private boolean isTyping = false;

    @PrePersist
    public void prePersist() {
        if (joinedAt == null) {
            joinedAt = Instant.now();
        }
    }
}
