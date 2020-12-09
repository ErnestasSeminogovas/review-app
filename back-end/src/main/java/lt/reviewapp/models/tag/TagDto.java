package lt.reviewapp.models.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {
    private Integer id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
