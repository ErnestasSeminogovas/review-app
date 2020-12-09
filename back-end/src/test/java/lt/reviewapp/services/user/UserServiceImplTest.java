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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void givenUsers_whenFindAll_thenReturnUsers() {
        User user = new User();
        UserDto userDto = new UserDto();

        given(userRepository.findAll()).willReturn(List.of(user));
        given(modelMapper.map(user, UserDto.class)).willReturn(userDto);

        List<UserDto> users = userService.findAll();

        assertThat(users).containsExactly(userDto);
    }

    @Test
    void givenUser_whenFindById_thenReturnUser() {
        User user = new User();
        UserDto userDto = new UserDto();

        given(userRepository.findById(anyInt())).willReturn(Optional.of(user));
        given(modelMapper.map(user, UserDto.class)).willReturn(userDto);

        UserDto foundUserDto = userService.findById(anyInt());

        assertThat(foundUserDto).isEqualTo(userDto);
    }

    @Test
    void givenNoUser_whenFindById_thenThrowEntityNotFoundException() {
        given(userRepository.findById(anyInt())).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(1));
    }

    @Test
    void givenUserWithNotExistingEmail_whenUpdateById_thenSaveUser() {
        UserRequest userRequest = new UserRequest("one@gmail.com");
        User user = User.builder().email("two@gmail.com").build();

        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(userRepository.existsByEmail(userRequest.getEmail())).willReturn(false);

        userService.updateById(1, userRequest);

        then(userRepository).should().save(user);
    }

    @Test
    void givenUserWithExistingEmail_whenUpdateById_thenThrowBadRequestException() {
        UserRequest userRequest = new UserRequest("one@gmail.com");
        User user = User.builder().email("two@gmail.com").build();

        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(userRepository.existsByEmail(userRequest.getEmail())).willReturn(true);

        assertThrows(BadRequestException.class, () -> userService.updateById(1, userRequest));
    }

    @Test
    void givenNoUser_whenUpdateById_thenEntityNotFoundThrown() {
        given(userRepository.findById(anyInt())).willReturn(Optional.empty());

        UserRequest userRequest = new UserRequest();
        assertThrows(EntityNotFoundException.class, () -> userService.updateById(1, userRequest));
    }

    @Test
    void givenUser_whenDeleteById_thenDeleteById() {
        given(userRepository.existsById(anyInt())).willReturn(true);

        userService.deleteById(anyInt());

        then(userRepository).should().deleteById(anyInt());
    }

    @Test
    void givenNoUser_whenDeleteById_thenEntityNotFoundThrown() {
        given(userRepository.existsById(anyInt())).willReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userService.deleteById(1));
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void givenUserOrNot_whenExistsByUsernameOrEmail_thenReturnCondition(boolean exists) {
        String username = "username";
        String email = "email@gmail.com";

        given(userRepository.existsByUsernameOrEmail(username, email)).willReturn(exists);

        boolean found = userService.existsByUsernameOrEmail(username, email);

        assertThat(found).isEqualTo(exists);
    }

    @Test
    void givenNoUserRole_whenCreate_thenThrowEntityNotFoundException() {
        given(roleRepository.findByName(RoleName.ROLE_USER)).willReturn(Optional.empty());

        RegisterRequest registerRequest = new RegisterRequest();
        assertThrows(EntityNotFoundException.class, () -> userService.create(registerRequest));
    }

    @Test
    void givenRegisterRequest_whenCreate_thenSaveNewUser() {
        Role role = new Role(RoleName.ROLE_USER);
        User createdUser = new User();
        createdUser.setId(1);
        RegisterRequest registerRequest = RegisterRequest.builder().password("qwerty").build();

        given(roleRepository.findByName(RoleName.ROLE_USER)).willReturn(Optional.of(role));
        given(passwordEncoder.encode(registerRequest.getPassword())).willReturn("encoded-qwerty");
        given(userRepository.save(any())).willReturn(createdUser);

        Integer userId = userService.create(registerRequest);

        assertThat(userId).isEqualTo(createdUser.getId());
    }
}