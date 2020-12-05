package lt.reviewapp.models.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank
    @Size(min = 1, max = 255)
    private String username;
    @NotBlank
    @Email
    @Size(min = 1, max = 255)
    private String email;
    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
}
