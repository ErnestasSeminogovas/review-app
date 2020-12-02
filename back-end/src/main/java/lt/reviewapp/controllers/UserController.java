package lt.reviewapp.controllers;

import lt.reviewapp.models.user.UserDto;
import lt.reviewapp.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public void updateById(@PathVariable Integer id, @RequestBody @Valid UserDto userDto) {
        userService.updateById(id, userDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
    }
}
