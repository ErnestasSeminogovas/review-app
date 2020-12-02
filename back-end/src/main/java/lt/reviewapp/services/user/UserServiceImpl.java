package lt.reviewapp.services.user;

import lt.reviewapp.configs.controller.exceptions.BadRequestException;
import lt.reviewapp.entities.User;
import lt.reviewapp.models.user.UserDto;
import lt.reviewapp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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

        if (!user.getUsername().equals(userDto.getUsername()) && userRepository.existsByUsername(userDto.getUsername())) {
            throw new BadRequestException("Username already exists: " + userDto.getUsername());
        }

        if (!user.getUsername().equals(userDto.getUsername()) && userRepository.existsByUsername(userDto.getUsername())) {
            throw new BadRequestException("Username already exists: " + userDto.getUsername());
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

    private EntityNotFoundException createEntityNotFoundException(Integer id) {
        return new EntityNotFoundException("User not found by id: " + id);
    }
}
