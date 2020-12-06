package lt.reviewapp.entities;

import lombok.Getter;
import lombok.Setter;
import lt.reviewapp.entities.common.CommonAuditorEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends CommonAuditorEntity {
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isPrimary;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Review review;
}
