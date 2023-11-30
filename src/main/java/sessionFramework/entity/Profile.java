package sessionFramework.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(
    name = "profile"
)
public class Profile {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "profile_id_gen"
    )
    @SequenceGenerator(
        name = "profile_id_gen",
        sequenceName = "profile_id_profile_seq",
        allocationSize = 1
    )
    @Column(
        name = "id_profile",
        nullable = false
    )
    private Integer id;
    @Column(
        name = "de_profile",
        nullable = false,
        length = 100
    )
    private String deProfile;
    @OneToMany(
        mappedBy = "idProfile"
    )
    private Set<Permission> permissions = new LinkedHashSet();
    @OneToMany(
        mappedBy = "idProfile"
    )
    private Set<UserProfile> userProfiles = new LinkedHashSet();

    public Profile() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getDeProfile() {
        return this.deProfile;
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    public Set<UserProfile> getUserProfiles() {
        return this.userProfiles;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setDeProfile(final String deProfile) {
        this.deProfile = deProfile;
    }

    public void setPermissions(final Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public void setUserProfiles(final Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }
}