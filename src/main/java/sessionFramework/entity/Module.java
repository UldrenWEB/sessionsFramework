package sessionFramework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_id_gen")
    @SequenceGenerator(name = "module_id_gen", sequenceName = "module_id_module_seq", allocationSize = 1)
    @Column(name = "id_module", nullable = false)
    private Integer id;

    @Column(name = "no_module", nullable = false, length = 50)
    private String noModule;

    @OneToMany(mappedBy = "idModule")
    private Set<BoObject> boObjects = new LinkedHashSet<>();

}