package woowacrew.article.crew.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import woowacrew.article.crew.domain.CrewArticle;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.dto.ArticleUpdateDto;
import woowacrew.degree.domain.Degree;
import woowacrew.search.domain.SearchSpec;
import woowacrew.search.domain.SearchType;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrewArticleServiceTest {
    @Mock
    private CrewArticleInternalService crewArticleInternalService;

    @InjectMocks
    private CrewArticleService crewArticleService;

    @Test
    void 원하는_게시물을_정상적으로_저장하는지_테스트한다() {
        String title = "title";
        String content = "content";
        ArticleRequestDto articleRequestDto = new ArticleRequestDto(title, content);
        UserContext userContext = new UserContext("userId");
        CrewArticle crewArticle = new CrewArticle(title, content, new User("userId", new Degree()));
        when(crewArticleInternalService.save(articleRequestDto, userContext)).thenReturn(crewArticle);

        ArticleResponseDto savedArticle = crewArticleService.save(articleRequestDto, userContext);

        assertThat(savedArticle.getTitle()).isEqualTo(title);
        assertThat(savedArticle.getContent()).isEqualTo(content);
    }

    @Test
    void 원하는_게시글을_ArticleResponse로_변환해준다() {
        UserContext userContext = new UserContext("oauthId");
        CrewArticle article = new CrewArticle("title", "content", new User("userId", new Degree()));
        Long articleId = 1L;
        when(crewArticleInternalService.findById(articleId, userContext)).thenReturn(article);

        ArticleResponseDto articleResponseDto = crewArticleService.findById(articleId, userContext);

        assertThat(articleResponseDto.getTitle()).isEqualTo("title");
        assertThat(articleResponseDto.getContent()).isEqualTo("content");
        assertThat(articleResponseDto.getUserResponseDto().getOauthId()).isEqualTo("userId");
    }

    @Test
    void 해당_크루기수의_전체_게시글을_ArticleResponse로_변환해준다() {
        List<CrewArticle> articles = createArticles(10);
        UserContext userContext = new UserContext("oauthId");
        Pageable pageable = PageRequest.of(1, 10);
        when(crewArticleInternalService.findAllByCrew(userContext, pageable)).thenReturn(new PageImpl<>(articles));

        ArticleResponseDtos articleResponseDtos = crewArticleService.findAllByCrew(userContext, pageable);

        assertThat(articleResponseDtos.getArticles().size()).isEqualTo(10);
    }

    @Test
    void update() {
        String oauthId = "oauthId";
        UserContext userContext = new UserContext(oauthId);
        User author = new User(oauthId, new Degree());
        CrewArticle crewArticle = new CrewArticle("title", "content", author);
        ArticleUpdateDto articleUpdateDto = new ArticleUpdateDto(1L, "updateTitle", "updateContent");
        crewArticle.update(author, articleUpdateDto.getTitle(), articleUpdateDto.getContent());

        when(crewArticleInternalService.update(articleUpdateDto, userContext)).thenReturn(crewArticle);

        ArticleResponseDto updatedArticle = crewArticleService.update(articleUpdateDto, userContext);

        assertThat(updatedArticle.getTitle()).isEqualTo("updateTitle");
        assertThat(updatedArticle.getContent()).isEqualTo("updateContent");
    }

    private List<CrewArticle> createArticles(int numberOfArticle) {
        User user = new User("userId", new Degree());
        List<CrewArticle> articles = new ArrayList<>();
        for (int i = 0; i < numberOfArticle; i++) {
            articles.add(new CrewArticle(String.valueOf(i), String.valueOf(i), user));
        }
        return articles;
    }

    @Test
    void 정상적으로_검색한다() {
        String title = "hello";
        String content = "bonjour";
        User user = new User("userId", new Degree());

        List<CrewArticle> crewArticles = new ArrayList<>();
        crewArticles.add(new CrewArticle(title, content, user));
        crewArticles.add(new CrewArticle(title, content, user));

        Page<CrewArticle> crewArticlePage = new PageImpl<>(crewArticles);
        SearchSpec<CrewArticle> searchSpec = SearchSpec.init("title", title, SearchType.values());
        UserContext userContext = new UserContext("userId");
        PageRequest pageable = PageRequest.of(1, 10);

        when(crewArticleInternalService.findSearchedArticles(searchSpec, pageable, userContext)).thenReturn(crewArticlePage);

        ArticleResponseDtos searchedArticles = crewArticleService.findSearchedArticles(searchSpec, pageable, userContext);

        List<ArticleResponseDto> articles = searchedArticles.getArticles();
        assertEquals(2, articles.size());
        for (ArticleResponseDto article : articles) {
            assertTrue(article.getTitle().contains("hello"));
        }
    }
}