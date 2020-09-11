package woowacrew.github.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woowacrew.user.domain.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GithubCommitRepository extends JpaRepository<GithubCommit, Long> {

    List<GithubCommit> findByDateOrderByPointDesc(LocalDate date);

    List<GithubCommit> findTop50ByDateOrderByPointDesc(LocalDate date);

    boolean existsByUserAndDate(User user, LocalDate date);
}
