package example.entities;

import javax.persistence.Embeddable;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Embeddable
public class DemoId implements Serializable {
    private Long parent;
    private Date date;

    public DemoId() {
    }

    public DemoId(final Long parent, final Date date) {
        this.parent = parent;
        this.date = date;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(final Long parent) {
        this.parent = parent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, parent);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DemoId other = (DemoId) obj;
        return Objects.equals(date, other.date) && Objects.equals(parent, other.parent);
    }
}
