package woowacrew.article.free.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.degree.domain.Degree;
import woowacrew.search.domain.SearchSpec;
import woowacrew.search.domain.SearchType;
import woowacrew.user.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    private ArticleInternalService articleInternalService;

    @InjectMocks
    private ArticleService articleService;

    @Test
    void 원하는_게시글을_ArticleResponse로_변환해준다() {
        Article article = new Article("title", "content", new User("userId", new Degree()));
        Long articleId = 1L;
        when(articleInternalService.findById(articleId)).thenReturn(article);

        ArticleResponseDto articleResponseDto = articleService.findById(articleId);

        assertThat(articleResponseDto.getTitle()).isEqualTo("title");
        assertThat(articleResponseDto.getContent()).isEqualTo("content");
        assertThat(articleResponseDto.getUserResponseDto().getOauthId()).isEqualTo("userId");
    }

    @Test
    void 전체_게시글을_ArticleResponse로_변환해준다() {
        List<Article> articles = createArticles(10);
        Pageable pageable = PageRequest.of(1, 10);
        when(articleInternalService.findAll(pageable)).thenReturn(new PageImpl<>(articles));

        ArticleResponseDtos articleResponseDtos = articleService.findAll(pageable);

        assertThat(articleResponseDtos.getArticles().size()).isEqualTo(10);
    }

    private List<Article> createArticles(int numberOfArticle) {
        User user = new User("userId", new Degree());
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < numberOfArticle; i++) {
            articles.add(new Article(String.valueOf(i), String.valueOf(i), user));
        }
        return articles;
    }

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
        SearchSpec<Article> searchSpec = SearchSpec.init("title", "delete", SearchType.values());
        Specification<Article> specification = searchSpec.getSpecification();

        when(articleInternalService.findAll(any(), any())).thenReturn(articlePages);

        ArticleResponseDtos actual = articleService.findSearchedArticles(specification, PageRequest.of(0, 20));

        assertThat(actual.getArticles().size()).isEqualTo(3);
        assertThat(actual.getPageNumber()).isEqualTo(1);
        assertThat(actual.getTotalPages()).isEqualTo(1);
    }
}