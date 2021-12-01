package example.entities;

import javax.persistence.*;

import java.sql.Date;

@Entity
@IdClass(DemoId.class)
public class DemoChild {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent", referencedColumnName = "id", nullable = false)
    private DemoParent parent;

    @Id
    private Date date;
}
