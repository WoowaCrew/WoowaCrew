package woowacrew.keyword.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByContent(String content);

    List<Keyword> findTop10ByOrderByViewsDesc();
}
