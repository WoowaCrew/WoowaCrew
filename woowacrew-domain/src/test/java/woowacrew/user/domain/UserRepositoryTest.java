package woowacrew.user.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import woowacrew.JpaTestConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 인가된_유저_목록_조회() {
        List<User> users = userRepository.findByRoleNotInAndNicknameNotNull(UserRole.ROLE_PRECOURSE);

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
        List<User> users = userRepository.findByDegreeIdAndNicknameNotNull(degreeId);

        assertThat(users.size()).isNotZero();
    }

    @Test
    void 기수에_맞는_유저의_수를_출력한다() {
        Long degreeId = 1L;
        int numberOfUser = userRepository.countByDegreeIdAndNicknameNotNull(degreeId);

        assertThat(numberOfUser).isNotZero();
    }

    @Test
    void 깃헙_아이디가_있으면_가져온다() {
        List<User> result = userRepository.findByGithubIdIsNotNull();

        assertThat(result.size()).isNotZero();
    }
}