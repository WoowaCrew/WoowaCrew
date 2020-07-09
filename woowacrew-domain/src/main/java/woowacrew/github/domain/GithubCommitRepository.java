package woowacrew.github.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubCommitRepository extends JpaRepository<GithubCommit, Long> {
}
