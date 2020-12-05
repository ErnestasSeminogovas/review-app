package lt.reviewapp.controllers;

import lt.reviewapp.configs.controller.exceptions.BadRequestException;
import lt.reviewapp.models.auth.LoginRequest;
import lt.reviewapp.models.auth.LoginResponse;
import lt.reviewapp.models.auth.RegisterRequest;
import lt.reviewapp.models.auth.TokenDto;
import lt.reviewapp.services.auth.AuthService;
import lt.reviewapp.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (userService.existsByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail())) {
            throw new BadRequestException("User already exists with this username or email.");
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{id}")
                .buildAndExpand(userService.create(registerRequest)).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refreshToken(@RequestBody @Valid TokenDto refreshToken) {
        return ResponseEntity.ok(new TokenDto(authService.generateAccessToken(refreshToken.getToken())));
    }
}
