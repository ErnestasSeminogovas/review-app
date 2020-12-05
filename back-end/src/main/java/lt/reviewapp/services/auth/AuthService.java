package lt.reviewapp.services.auth;

import lt.reviewapp.models.auth.LoginRequest;
import lt.reviewapp.models.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    String generateAccessToken(String refreshToken);
}
