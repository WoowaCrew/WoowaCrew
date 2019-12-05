package woowacrew.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(String oauthId);

    List<User> findByRoleNotIn(UserRole role);

    List<User> findByRole(UserRole role);
}
