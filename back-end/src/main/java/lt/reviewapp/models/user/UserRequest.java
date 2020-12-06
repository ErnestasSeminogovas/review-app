package lt.reviewapp.models.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRequest {
    @NotBlank
    @Email
    @Size(min = 1, max = 255)
    private String email;
}
