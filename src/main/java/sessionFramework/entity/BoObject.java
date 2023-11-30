package sessionFramework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bo_object")
public class BoObject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bo_object_id_gen")
    @SequenceGenerator(name = "bo_object_id_gen", sequenceName = "bo_object_id_object_seq", allocationSize = 1)
    @Column(name = "id_object", nullable = false)
    private Integer id;

    @Column(name = "no_object", nullable = false, length = 50)
    private String noObject;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_module", nullable = false)
    private Module idModule;

    @OneToMany(mappedBy = "idObject")
    private Set<BoMethod> boMethods = new LinkedHashSet<>();

}


