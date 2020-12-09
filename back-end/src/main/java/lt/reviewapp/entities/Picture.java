package lt.reviewapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lt.reviewapp.entities.common.CommonAuditorEntity;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
