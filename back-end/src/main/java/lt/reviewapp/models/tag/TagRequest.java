package lt.reviewapp.models.tag;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TagRequest {
    @NotBlank
    @Size(max = 255)
    private String title;
}
