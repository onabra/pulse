package ir.onabra.pulse.entity;


import ir.onabra.pulse.base.BaseAuditableEntity;
import ir.onabra.pulse.enums.ChatType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
        name = "chats",
        indexes = {
                @Index(
                        name = "idx_chats_type",
                        columnList = "type"
                ),
                @Index(
                        name = "idx_chats_created_by_user_id",
                        columnList = "created_by_user_id"
                ),
                @Index(
                        name = "idx_chats_last_message_at",
                        columnList = "last_message_at"
                )
        }
)
public class Chat extends BaseAuditableEntity {

    @Enumerated(EnumType.STRING)
    @Column(
            name = "type",
            nullable = false,
            length = 20
    )
    private ChatType type;

    @Column(
            name = "title",
            length = 200
    )
    private String title;

    @Column(
            name = "description",
            columnDefinition = "text"
    )
    private String description;

    @Column(
            name = "avatar_url",
            length = 2048
    )
    private String avatarUrl;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "created_by_user_id",
            foreignKey = @ForeignKey(
                    name = "fk_chats_created_by_user"
            )
    )
    private UserEntity createdByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "last_message_id",
            foreignKey = @ForeignKey(
                    name = "fk_chats_last_message"
            )
    )
    private Message lastMessage;

    @Column(name = "last_message_at")
    private Instant lastMessageAt;

    @Column(name = "members_count", nullable = false)
    private int membersCount = 0;

    @Column(name = "online_members_count", nullable = false)
    private int onlineMembersCount = 0;
}
