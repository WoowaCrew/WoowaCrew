package woowacrew.article.domain;

import org.junit.jupiter.api.Test;
import woowacrew.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleConverterTest {
    @Test
    void ArticleToArticleResponse() {
        String title = "hello";
        String content = "bonjour";
        User user = new User("userId", "url");
        Article article = new Article(title, content, user);

        ArticleResponse articleResponse = ArticleConverter.articleToArticleResponseDto(article);
        assertThat(articleResponse.getTitle()).isEqualTo(title);
        assertThat(articleResponse.getContent()).isEqualTo(content);
    }
}