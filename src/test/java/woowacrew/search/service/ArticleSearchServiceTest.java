package woowacrew.search.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleSearchServiceTest {

    @Mock
    private Pageable mockPageable;

    @Mock
    private ArticleSearchInternalService mockArticleSearchInternalService;

    @InjectMocks
    private ArticleSearchService articleSearchService;

    @Test
    void 정상적으로_제목으로_게시글을_검색한다() {
        String title = "hello";
        String content = "bonjour";
        User user = new User("userId", new Degree());

        List<Article> articles = new ArrayList<>();
        articles.add(new Article(title, content, user));
        articles.add(new Article(title, content, user));
        articles.add(new Article(title, content, user));

        Page<Article> articlePages = new PageImpl<>(articles);

        when(mockArticleSearchInternalService.findAll(any(), any())).thenReturn(articlePages);

        ArticleResponseDtos actual = articleSearchService.findAll("title", "delete", mockPageable);

        assertThat(actual.getArticles().size()).isEqualTo(3);
        assertThat(actual.getPageNumber()).isEqualTo(1);
        assertThat(actual.getTotalPages()).isEqualTo(1);
    }
}