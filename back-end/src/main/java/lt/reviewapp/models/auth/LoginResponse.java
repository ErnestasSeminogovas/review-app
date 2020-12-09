package lt.reviewapp.models.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Integer userId;
    private String accessToken;
    private String refreshToken;
    private List<String> authorities;
}
