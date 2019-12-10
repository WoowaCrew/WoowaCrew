package woowacrew.article.domain;

import org.junit.jupiter.api.Test;
import woowacrew.user.domain.Degree;
import woowacrew.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleConverterTest {
    @Test
    void ArticleToArticleResponse() {
        String title = "hello";
        String content = "bonjour";
        User user = new User("userId", new Degree());
        Article article = new Article(title, content, user);

        ArticleResponseDto articleResponseDto = ArticleConverter.articleToArticleResponseDto(article);
        assertThat(articleResponseDto.getTitle()).isEqualTo(title);
        assertThat(articleResponseDto.getContent()).isEqualTo(content);
    }
}