package lt.reviewapp.models.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    @Size(max = 255)
    private String username;
    @NotBlank
    @Size(max = 255)
    private String password;
}
