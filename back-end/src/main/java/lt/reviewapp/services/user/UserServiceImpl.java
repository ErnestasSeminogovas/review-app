package lt.reviewapp.services.user;

import lt.reviewapp.configs.controller.exceptions.BadRequestException;
import lt.reviewapp.entities.user.Role;
import lt.reviewapp.entities.user.RoleName;
import lt.reviewapp.entities.user.User;
import lt.reviewapp.models.auth.RegisterRequest;
import lt.reviewapp.models.user.UserDto;
import lt.reviewapp.models.user.UserRequest;
import lt.reviewapp.repositories.RoleRepository;
import lt.reviewapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(this::mapToUserDto).collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        return UserDto.builder().username(user.getUsername()).email(user.getEmail()).build();
    }

    @Override
    public UserDto findById(Integer id) {
        User user =
                userRepository.findById(id).orElseThrow(() -> createEntityNotFoundException(id));

        return mapToUserDto(user);
    }

    @Transactional
    @Override
    public void updateById(Integer id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> createEntityNotFoundException(id));

        if (!user.getEmail().equals(userRequest.getEmail()) && userRepository.existsByEmail(
                userRequest.getEmail())) {
            throw new BadRequestException("User already exists with this email.");
        }

        user.setEmail(userRequest.getEmail());

        userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw createEntityNotFoundException(id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsernameOrEmail(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }

    @Override
    public Integer create(RegisterRequest registerRequest) {
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User role was not found by name: " + RoleName.ROLE_USER.name()));

        User user =
                User.builder().username(registerRequest.getUsername()).email(registerRequest.getEmail())
                        .password(passwordEncoder.encode(registerRequest.getPassword())).authorities(List.of(role))
                        .build();

        return userRepository.save(user).getId();
    }

    private EntityNotFoundException createEntityNotFoundException(Integer id) {
        return new EntityNotFoundException("User not found by id: " + id);
    }
}
