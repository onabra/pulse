package ir.onabra.pulse.entity;


import ir.onabra.pulse.base.BaseCreatedEntity;
import ir.onabra.pulse.enums.FriendRequestStatus;
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
        name = "friend_requests",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_friend_requests_sender_receiver",
                        columnNames = {
                                "sender_user_id",
                                "receiver_user_id"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_friend_requests_sender_status",
                        columnList = "sender_user_id, status"
                ),
                @Index(
                        name = "idx_friend_requests_receiver_status",
                        columnList = "receiver_user_id, status"
                ),
                @Index(
                        name = "idx_friend_requests_created_at",
                        columnList = "created_at"
                )
        }
)
public class FriendRequest extends BaseCreatedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "sender_user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_friend_requests_sender_user"
            )
    )
    private UserEntity senderUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "receiver_user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_friend_requests_receiver_user"
            )
    )
    private UserEntity receiverUser;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private FriendRequestStatus status = FriendRequestStatus.PENDING;

    @Column(name = "responded_at")
    private Instant respondedAt;

    public void accept() {
        status = FriendRequestStatus.ACCEPTED;
        respondedAt = Instant.now();
    }

    public void reject() {
        status = FriendRequestStatus.REJECTED;
        respondedAt = Instant.now();
    }

    public void cancel() {
        status = FriendRequestStatus.CANCELLED;
        respondedAt = Instant.now();
    }
}
