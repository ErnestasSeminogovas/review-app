package lt.reviewapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends CommonEntity {
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isPrimary;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Review review;
}
