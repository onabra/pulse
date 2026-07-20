package ir.onabra.pulse.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "user_profiles")
public class UserProfileEntity {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(columnDefinition = "text")
    private String bio;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 30)
    private String gender;

    @Column(length = 200)
    private String location;

    @Column(length = 2048)
    private String website;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
