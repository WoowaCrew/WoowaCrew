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
import woowacrew.article.free.domain.ArticleRepository;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleUpdateDto;
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.article.free.exception.MisMatchUserException;
import woowacrew.article.free.exception.NotFoundArticleException;
import woowacrew.common.service.FieldSetter;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleInternalServiceTest {
    @Mock
    private Specification<Article> mockArticleSpecification;
    @Mock
    private Page<Article> mockArticlePages;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserInternalService userInternalService;

    @InjectMocks
    private ArticleInternalService articleInternalService;

    @Test
    void 게시글_생성_테스트() {
        UserContext userContext = new UserContext("asd");
        User user = new User("asd", new Degree());
        ArticleRequestDto articleRequestDto = new ArticleRequestDto("hello", "bonjour");

        when(userInternalService.findByOauthId(userContext.getOauthId())).thenReturn(user);

        Article article = articleInternalService.save(articleRequestDto, userContext);

        assertThat(article.getTitle()).isEqualTo("hello");
        assertThat(article.getContent()).isEqualTo("bonjour");
    }

    @Test
    void 게시글_조회_테스트() {
        String title = "title";
        String content = "content";
        User user = new User("asd", new Degree());
        Article article = new Article(title, content, user);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        Article expectedArticle = articleInternalService.findById(1L);
        assertThat(expectedArticle.getTitle()).isEqualTo(title);
        assertThat(expectedArticle.getContent()).isEqualTo("content");
    }

    @Test
    void 없는_게시글_조회_테스트() {
        String title = "title";
        String content = "content";
        User user = new User("asd", new Degree());
        Article article = new Article(title, content, user);
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundArticleException.class, () -> articleInternalService.findById(1L));
    }

    @Test
    void 게시글_전체_조회_테스트() {
        List<Article> articles = createArticles(ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE);
        Pageable pageable = PageRequest.of(1, ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE);
        when(articleRepository.findAll(pageable)).thenReturn(new PageImpl<>(articles));

        Page<Article> actualArticles = articleInternalService.findAll(pageable);

        assertThat(actualArticles.getTotalElements()).isEqualTo(ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE);
    }

    @Test
    void 게시글_페이징_사이즈가_20이_아니면_에러가_발생한다() {
        Pageable pageable = PageRequest.of(1, 5);

        assertThatThrownBy(() -> articleInternalService.findAll(pageable)).isInstanceOf(InvalidPageRequstException.class);
    }

    @Test
    void 게시글_업데이트_작성자일_경우_테스트() {
        String title = "title";
        String content = "content";
        String updateTitle = "title1";
        String updateContent = "content1";

        UserContext userContext = new UserContext("asd");
        User user = new User("asd", new Degree());
        FieldSetter.set(user, "id", 1L);

        Article article = new Article(title, content, user);
        ArticleUpdateDto articleUpdateDto = new ArticleUpdateDto(1L, updateTitle, updateContent);
        when(userInternalService.findById(userContext.getId())).thenReturn(user);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        Article updateArticle = articleInternalService.update(articleUpdateDto, userContext);

        assertThat(updateArticle.getId()).isEqualTo(article.getId());
        assertThat(updateArticle.getTitle()).isEqualTo(updateTitle);
        assertThat(updateArticle.getContent()).isEqualTo(updateContent);
    }

    @Test
    void 게시글_업데이트_작성자가_아닐_경우_테스트() {
        String title = "title";
        String content = "content";
        String updateTitle = "title1";
        String updateContent = "content1";

        UserContext userContext = new UserContext("asd");
        User user1 = new User("asd", new Degree());
        FieldSetter.set(user1, "id", 1L);

        User user2 = new User("asd", new Degree());
        FieldSetter.set(user2, "id", 2L);

        Article article = new Article(title, content, user1);
        ArticleUpdateDto articleUpdateDto = new ArticleUpdateDto(1L, updateTitle, updateContent);
        when(userInternalService.findById(userContext.getId())).thenReturn(user2);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        assertThrows(MisMatchUserException.class, () -> articleInternalService.update(articleUpdateDto, userContext));
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
    void 페이지_개수가_20개가_아니면_에러가_발생한다() {
        Pageable differentPageable = PageRequest.of(0, 19);
        assertThrows(InvalidPageRequstException.class, () -> articleInternalService.findAll(mockArticleSpecification, differentPageable));
    }

    @Test
    void 정상적으로_제목이_포함된_게시글을_찾는다() {
        Pageable pageable = PageRequest.of(0, 20);
        when(articleRepository.findAll(mockArticleSpecification, pageable)).thenReturn(mockArticlePages);

        assertDoesNotThrow(() -> articleInternalService.findAll(mockArticleSpecification, pageable));
    }
}