package lt.reviewapp.services.user;

import lt.reviewapp.entities.user.User;
import lt.reviewapp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void givenUser_whenLoadUserByUsername_thenFindByUsername() {
        User user = User.builder().username("test").build();
        given(userRepository.findByUsername("test")).willReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername("test");

        assertThat(userDetails).isEqualTo(user);
    }
}