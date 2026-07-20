package ir.onabra.pulse.entity;


import ir.onabra.pulse.base.BaseCreatedEntity;
import ir.onabra.pulse.enums.NotificationType;
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
        name = "notifications",
        indexes = {
                @Index(
                        name = "idx_notifications_user_read_created",
                        columnList = "user_id, is_read, created_at"
                ),
                @Index(
                        name = "idx_notifications_type",
                        columnList = "type"
                )
        }
)
public class Notification extends BaseCreatedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_notifications_user"
            )
    )
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "type",
            nullable = false,
            length = 30
    )
    private NotificationType type;

    @Column(
            name = "title",
            nullable = false,
            length = 200
    )
    private String title;

    @Column(
            name = "body",
            columnDefinition = "text"
    )
    private String body;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Column(
            name = "action_url",
            length = 2048
    )
    private String actionUrl;

    @Column(name = "read_at")
    private Instant readAt;

    public void markAsRead() {
        if (!isRead) {
            isRead = true;
            readAt = Instant.now();
        }
    }

    public void markAsUnread() {
        isRead = false;
        readAt = null;
    }
}
