package lt.reviewapp.services.user;

import lt.reviewapp.models.auth.RegisterRequest;
import lt.reviewapp.models.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Integer id);

    void updateById(Integer id, UserDto userDto);

    void deleteById(Integer id);

    boolean existsByUsernameOrEmail(String username, String email);

    Integer create(RegisterRequest registerRequest);
}
