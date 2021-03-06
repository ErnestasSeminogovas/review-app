package lt.reviewapp.repositories;

import lt.reviewapp.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);

    User findByUsername(String username);
}
