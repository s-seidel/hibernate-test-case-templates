package demo;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.annotations.Where;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @Where(clause = "\"start\" <= cast(current_timestamp as date) and (\"end\" >= cast(current_timestamp as date) or \"end\" is null)")
    private Set<SkillAssignment> currentSkills;
}
