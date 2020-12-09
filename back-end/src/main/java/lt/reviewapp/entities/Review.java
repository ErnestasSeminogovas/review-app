package lt.reviewapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lt.reviewapp.entities.common.CommonAuditorEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "reviews")
public class Review extends CommonAuditorEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private Integer rating;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> pictures = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "review_tag", joinColumns = @JoinColumn(name = "review_id"), inverseJoinColumns =
    @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();
}
