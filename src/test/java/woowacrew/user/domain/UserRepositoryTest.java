package woowacrew.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 인가된_유저_목록_조회() {
        List<User> users = userRepository.findByRoleNotIn(UserRole.ROLE_PRECOURSE);

        for (User user : users) {
            UserRole userRole = user.getRole();
            assertTrue(userRole.isApproved());
        }
    }

    @Test
    void 인가되지_않은_유저_목록_조회() {
        List<User> users = userRepository.findByRole(UserRole.ROLE_PRECOURSE);

        for (User user : users) {
            UserRole userRole = user.getRole();
            assertFalse(userRole.isApproved());
        }
    }
}