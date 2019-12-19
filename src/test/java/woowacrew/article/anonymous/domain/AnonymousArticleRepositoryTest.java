package woowacrew.article.anonymous.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AnonymousArticleRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(AnonymousArticleRepositoryTest.class);

    @Autowired
    private AnonymousArticleRepository anonymousArticleRepository;

    @Test
    @DisplayName("승인된 익명 게시글 목록 조회")
    void approvedAnonymousArticleList() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<AnonymousArticle> articles = anonymousArticleRepository.findByIsApproved(true, pageable);


        articles.forEach(anonymousArticle -> assertTrue(anonymousArticle.isApproved()));
    }

    @Test
    @DisplayName("승인되지 않은 익명 게시글 목록 조회")
    void unapprovedAnonymousArticleList() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<AnonymousArticle> articles = anonymousArticleRepository.findByIsApproved(false, pageable);

        articles.forEach(anonymousArticle -> assertFalse(anonymousArticle.isApproved()));
    }
}