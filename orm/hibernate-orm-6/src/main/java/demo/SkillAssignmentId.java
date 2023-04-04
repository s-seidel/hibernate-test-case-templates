package demo;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class SkillAssignmentId implements Serializable {
    private Long employee;
    private Long skill;

    public SkillAssignmentId(final Long employee, final Long skill) {
        this.employee = employee;
        this.skill = skill;
    }

    public Long getEmployee() {
        return employee;
    }

    public Long getSkill() {
        return skill;
    }
}
