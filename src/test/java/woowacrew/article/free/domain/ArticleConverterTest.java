package woowacrew.article.free.domain;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.utils.ArticleConverter;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleConverterTest {
    @Test
    void ArticleToArticleResponse() {
        String title = "hello";
        String content = "bonjour";
        User user = new User("userId", new Degree());
        Article article = new Article(title, content, user);

        ArticleResponseDto articleResponseDto = ArticleConverter.toDto(article);
        assertThat(articleResponseDto.getTitle()).isEqualTo(title);
        assertThat(articleResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    void articlePagesToArticleResponseDtos() {
        String title = "hello";
        String content = "bonjour";
        User user = new User("userId", new Degree());

        List<Article> articles = new ArrayList<>();
        articles.add(new Article(title, content, user));
        articles.add(new Article(title, content, user));
        articles.add(new Article(title, content, user));

        Page<Article> articlePages = new PageImpl<>(articles);

        List<ArticleResponseDto> articleResponseDtos = ArticleConverter.toDtos(articlePages);
        assertThat(articleResponseDtos.size()).isEqualTo(3);
    }
}