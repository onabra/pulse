package ir.onabra.pulse.base;


import ir.onabra.pulse.enums.PublishStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

import java.time.Instant;

@MappedSuperclass
public abstract class BasePublishableEntity extends BaseAuditableEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "publish_status", nullable = false, length = 20)
    protected PublishStatus publishStatus = PublishStatus.DRAFT;

    @Column(name = "published_at")
    protected Instant publishedAt;

    @Column(name = "scheduled_at")
    protected Instant scheduledAt;
}
