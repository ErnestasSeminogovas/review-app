package lt.reviewapp.services.auth;

import lt.reviewapp.configs.security.TokenProvider;
import lt.reviewapp.entities.User;
import lt.reviewapp.models.auth.LoginRequest;
import lt.reviewapp.models.auth.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, TokenProvider tokenProvider,
                           UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                        , loginRequest.getPassword()));

        User user = (User) authentication.getPrincipal();

        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return LoginResponse.builder().userId(user.getId()).accessToken(tokenProvider.generateAccessToken(user.getUsername())).refreshToken(tokenProvider.generateRefreshToken(user.getUsername())).authorities(authorities).build();
    }

    @Override
    public String generateAccessToken(String refreshToken) {
        return tokenProvider.parseRefreshUsername(refreshToken).map(userDetailsService::loadUserByUsername).map(userDetails -> tokenProvider.generateAccessToken(userDetails.getUsername())).orElse("");
    }
}