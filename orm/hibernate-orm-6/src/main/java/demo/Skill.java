package demo;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.annotations.Where;

import jakarta.persistence.*;

@Entity
@Table(name = "skill")
public class Skill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
    @Where(clause = "start <= cast(current_timestamp as DATE) and (ende >= cast(current_timestamp as DATE) or ende is null)")
    private Set<SkillAssignment> currentAssignments;
}
