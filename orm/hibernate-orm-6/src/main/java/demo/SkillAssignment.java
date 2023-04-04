package demo;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.*;

@Entity
@IdClass(SkillAssignmentId.class)
@Table(name = "skillassignment")
public class SkillAssignment implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeeid", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @Column(insertable = false, updatable = false, nullable = false)
    private Long employeeid;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "skillid", referencedColumnName = "id", nullable = false)
    private Skill skill;

    @Column(insertable = false, updatable = false, nullable = false)
    private Long skillid;

    @Column(nullable = false)
    private Date start;

    private Date end;

    public SkillAssignment(final Employee employee, final Long employeeid, final Skill skill, final Long skillid,
            final Date start, final Date ende) {
        this.employee = employee;
        this.employeeid = employeeid;
        this.skill = skill;
        this.skillid = skillid;
        this.start = start;
        this.end = end;
    }
}
