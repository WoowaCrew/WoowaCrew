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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
class GithubCommitRepositoryTest {

    @Autowired
    private GithubCommitRepository githubCommitRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 정상적으로_1위부터_50위까지_커밋_랭킹을_가져온다() {
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            githubCommitRepository.save(new GithubCommit(users.get(i), LocalDate.of(2020, 6, 1), i * 100));
        }

        List<GithubCommit> result = githubCommitRepository.findByOrderByPointDesc();
        for (GithubCommit githubCommit : result) {
            assertNotNull(githubCommit);
            System.out.println(githubCommit.getPoint());
        }
        assertThat(result.size()).isEqualTo(users.size());
    }
}