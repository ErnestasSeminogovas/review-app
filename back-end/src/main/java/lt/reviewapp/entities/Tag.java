package lt.reviewapp.entities;

import lombok.Getter;
import lombok.Setter;

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
public class Tag extends CommonEntity {
    @Column(nullable = false, unique = true)
    private String title;

    @ManyToMany(mappedBy = "tags")
    List<Review> reviews = new ArrayList<>();
}
