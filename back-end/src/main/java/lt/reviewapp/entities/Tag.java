package lt.reviewapp.entities;

import lombok.Getter;
import lombok.Setter;
import lt.reviewapp.entities.common.CommonAuditorEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag extends CommonAuditorEntity {
    @Column(nullable = false, unique = true)
    private String title;

    @ManyToMany(mappedBy = "tags")
    List<Review> reviews = new ArrayList<>();
}
