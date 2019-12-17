package woowacrew.feed.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedSourceRepository extends JpaRepository<FeedSource, Long> {
    boolean existsBySourceUrl(String sourceUrl);
}
