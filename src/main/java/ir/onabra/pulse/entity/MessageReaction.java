package ir.onabra.pulse.entity;


import ir.onabra.pulse.base.BaseCreatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "message_reactions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_message_reactions_message_user_emoji",
                        columnNames = {
                                "message_id",
                                "user_id",
                                "emoji"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_message_reactions_message_id",
                        columnList = "message_id"
                ),
                @Index(
                        name = "idx_message_reactions_user_id",
                        columnList = "user_id"
                )
        }
)
public class MessageReaction extends BaseCreatedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "message_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_message_reactions_message"
            )
    )
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_message_reactions_user"
            )
    )
    private UserEntity user;

    @Column(
            name = "emoji",
            nullable = false,
            length = 32
    )
    private String emoji;
}
