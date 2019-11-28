package woowacrew.article.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleFromTest {

    @Test
    void 업데이트_테스트() {
        ArticleFrom articleFrom = new ArticleFrom("test", "testContent");
        articleFrom.updateArticle("test2", "testContent2");

        assertThat(articleFrom.getTitle()).isEqualTo("test2");
        assertThat(articleFrom.getContent()).isEqualTo("testContent2");
    }
}