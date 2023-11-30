package sessionFramework.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "se_user"
)
public class SeUser {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "se_user_id_gen"
    )
    @SequenceGenerator(
        name = "se_user_id_gen",
        sequenceName = "user_id_user_seq",
        allocationSize = 1
    )
    @Column(
        name = "id_user",
        nullable = false
    )
    private Integer id;
    @Column(
        name = "no_user",
        nullable = false,
        length = 100
    )
    private String noUser;
    @Column(
        name = "pw_user",
        nullable = false,
        length = 100
    )
    private String pwUser;
    @Column(
        name = "at_user",
        nullable = false
    )
    private Integer atUser;
    @Column(
        name = "bl_user",
        nullable = false
    )
    private Boolean blUser = false;
    @Column(
        name = "em_user",
        length = 50
    )
    private String emUser;

    public SeUser() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getNoUser() {
        return this.noUser;
    }

    public String getPwUser() {
        return this.pwUser;
    }

    public Integer getAtUser() {
        return this.atUser;
    }

    public Boolean getBlUser() {
        return this.blUser;
    }

    public String getEmUser() {
        return this.emUser;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setNoUser(final String noUser) {
        this.noUser = noUser;
    }

    public void setPwUser(final String pwUser) {
        this.pwUser = pwUser;
    }

    public void setAtUser(final Integer atUser) {
        this.atUser = atUser;
    }

    public void setBlUser(final Boolean blUser) {
        this.blUser = blUser;
    }

    public void setEmUser(final String emUser) {
        this.emUser = emUser;
    }
}


