package woowacrew.article.free.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleFormTest {

    @Test
    void 업데이트_테스트() {
        ArticleForm articleForm = new ArticleForm("test", "testContent");
        articleForm.update("test2", "testContent2");

        assertThat(articleForm.getTitle()).isEqualTo("test2");
        assertThat(articleForm.getContent()).isEqualTo("testContent2");
    }
}