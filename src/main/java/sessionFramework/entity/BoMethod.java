package sessionFramework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bo_method")
public class BoMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bo_method_id_gen")
    @SequenceGenerator(name = "bo_method_id_gen", sequenceName = "bo_method_id_object_seq", allocationSize = 1)
    @Column(name = "id_method", nullable = false)
    private Integer id;

    @Column(name = "no_method", nullable = false, length = 50)
    private String noMethod;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_object", nullable = false)
    private BoObject idObject;

    @OneToMany(mappedBy = "idMethod")
    private Set<Permission> permissions = new LinkedHashSet<>();

}
