package woowacrew.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
            assertTrue(user.isApproved());
        }
    }

    @Test
    void 인가되지_않은_유저_목록_조회() {
        List<User> users = userRepository.findByRole(UserRole.ROLE_PRECOURSE);

        for (User user : users) {
            assertFalse(user.isApproved());
        }
    }

    @Test
    void 닉네임이_등록된_유저_중_인가되지_않은_유저_목록_조회() {
        List<User> users = userRepository.findByRoleAndNicknameNotNull(UserRole.ROLE_PRECOURSE);

        assertThat(users.size()).isNotZero();
    }

    @Test
    void 기수에_맞는_유저를_출력한다() {
        Long degreeId = 1L;
        List<User> users = userRepository.findByDegreeId(degreeId);

        assertThat(users.size()).isNotZero();
    }

    @Test
    void 기수에_맞는_유저의_수를_출력한다() {
        Long degreeId = 1L;
        int numberOfUser = userRepository.countByDegreeId(degreeId);

        assertThat(numberOfUser).isNotZero();
    }
}