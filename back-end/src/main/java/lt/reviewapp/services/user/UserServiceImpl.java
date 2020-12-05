package lt.reviewapp.services.user;

import lt.reviewapp.configs.controller.exceptions.BadRequestException;
import lt.reviewapp.entities.User;
import lt.reviewapp.models.auth.RegisterRequest;
import lt.reviewapp.models.user.UserDto;
import lt.reviewapp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(tag -> modelMapper.map(tag, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        User user =
                userRepository.findById(id).orElseThrow(() -> createEntityNotFoundException(id));

        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    @Override
    public void updateById(Integer id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> createEntityNotFoundException(id));

        boolean usernameOrEmailChanged =
                !user.getUsername().equals(userDto.getUsername()) || !user.getEmail().equals(userDto.getEmail());
        if (usernameOrEmailChanged && userRepository.existsByUsernameOrEmail(userDto.getUsername(),
                userDto.getEmail())) {
            throw new BadRequestException("User already exists with this username or email.");
        }

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

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

    // TODO: add roles here
    @Override
    public Integer create(RegisterRequest registerRequest) {
        User user =
                User.builder().username(registerRequest.getUsername()).email(registerRequest.getEmail()).password(passwordEncoder.encode(registerRequest.getPassword())).build();

        return userRepository.save(user).getId();
    }

    private EntityNotFoundException createEntityNotFoundException(Integer id) {
        return new EntityNotFoundException("User not found by id: " + id);
    }
}
