package sessionFramework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_user_profile", nullable = false)
    private Long idUserProfile;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private SeUser idUser;

    @ManyToOne
    @JoinColumn(name = "id_profile", nullable = false)
    private Profile idProfile;

}