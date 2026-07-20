package ir.onabra.pulse.entity;


import ir.onabra.pulse.base.BaseCreatedEntity;
import ir.onabra.pulse.enums.AttachmentType;
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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "message_attachments",
        indexes = {
                @Index(
                        name = "idx_message_attachments_message_id",
                        columnList = "message_id"
                ),
                @Index(
                        name = "idx_message_attachments_file_type",
                        columnList = "file_type"
                )
        }
)
public class MessageAttachment extends BaseCreatedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "message_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_message_attachments_message"
            )
    )
    private Message message;

    @Column(
            name = "file_name",
            nullable = false,
            length = 255
    )
    private String fileName;

    @Column(
            name = "file_url",
            nullable = false,
            length = 2048
    )
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "file_type",
            nullable = false,
            length = 30
    )
    private AttachmentType fileType = AttachmentType.OTHER;

    @Column(
            name = "mime_type",
            length = 150
    )
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    /**
     * Duration in milliseconds.
     */
    @Column(name = "duration")
    private Long duration;
}
