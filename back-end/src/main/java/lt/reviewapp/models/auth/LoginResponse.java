package lt.reviewapp.models.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LoginResponse {
    private Integer userId;
    private String accessToken;
    private String refreshToken;
    private List<String> authorities;
}
