package lt.reviewapp.models.tag;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TagDto {
    private Integer id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
