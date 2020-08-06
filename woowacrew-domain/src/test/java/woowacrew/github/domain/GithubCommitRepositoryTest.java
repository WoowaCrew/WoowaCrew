package woowacrew.github.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import woowacrew.JpaTestConfiguration;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
class GithubCommitRepositoryTest {

    @Autowired
    private GithubCommitRepository githubCommitRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 정상적으로_전체_랭킹을_가져온다() {
        LocalDate date = LocalDate.of(2020, 6, 1);
        generateGithubCommit(date);

        List<GithubCommit> result = githubCommitRepository.findByDateOrderByPointDesc(date);
        for (GithubCommit githubCommit : result) {
            assertNotNull(githubCommit);
        }
    }

    @Test
    void 정상적으로_1위부터_50위까지_커밋_랭킹을_가져온다() {
        LocalDate date = LocalDate.of(2020, 6, 1);
        generateGithubCommit(date);

        List<GithubCommit> result = githubCommitRepository.findTop50ByDateOrderByPointDesc(date);
        assertTrue(result.size() <= 50);
    }

    private void generateGithubCommit(LocalDate date) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            int randomPoint = new Random().nextInt(300);
            githubCommitRepository.save(new GithubCommit(user, date, randomPoint));
        }
    }

    @Test
    void 유저와_날짜를_받아서_존재하는_정보가_없는_경우() {
        userRepository.findById(1L).ifPresent(user -> {
            boolean result = githubCommitRepository.existsByUserAndDate(user, LocalDate.of(1990, 5, 1));
            assertFalse(result);
        });
    }

    @Test
    void 유저와_날짜를_받아서_존재하는_정보가_있는_경우() {
        userRepository.findById(1L).ifPresent(user -> {
            LocalDate date = LocalDate.of(2020, 6, 1);
            githubCommitRepository.save(new GithubCommit(user, date, 100));
            boolean result = githubCommitRepository.existsByUserAndDate(user, date);
            assertTrue(result);
        });
    }
}