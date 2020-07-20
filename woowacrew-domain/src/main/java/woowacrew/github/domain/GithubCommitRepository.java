package woowacrew.github.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GithubCommitRepository extends JpaRepository<GithubCommit, Long> {
    List<GithubCommit> findByOrderByPointDesc();
}
