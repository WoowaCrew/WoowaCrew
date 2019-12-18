package woowacrew.article.anonymous.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AnonymousArticleRepositoryTest {

    @Autowired
    private AnonymousArticleRepository anonymousArticleRepository;

    @Test
    @DisplayName("승인된 익명 게시글 목록 조회")
    void approvedAnonymousArticleList() {
        List<AnonymousArticle> articles = anonymousArticleRepository.findByIsApproved(true);

        for (AnonymousArticle anonymousArticle : articles) {
            assertTrue(anonymousArticle.isApproved());
        }
    }

    @Test
    @DisplayName("승인되지 않은 익명 게시글 목록 조회")
    void unapprovedAnonymousArticleList() {
        List<AnonymousArticle> articles = anonymousArticleRepository.findByIsApproved(false);

        for (AnonymousArticle anonymousArticle : articles) {
            assertFalse(anonymousArticle.isApproved());
        }
    }
}