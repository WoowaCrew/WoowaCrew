package woowacrew.article.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.article.domain.Article;
import woowacrew.article.domain.ArticleRepository;
import woowacrew.article.domain.ArticleRequestDto;
import woowacrew.article.domain.ArticleUpdateDto;
import woowacrew.common.service.FieldSetter;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserContext;
import woowacrew.user.service.UserInternalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleInternalServiceTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserInternalService userInternalService;

    @InjectMocks
    private ArticleInternalService articleInternalService;

    @Test
    void 게시글_생성_테스트() {
        UserContext userContext = new UserContext("asd", "asd");
        User user = new User("asd", "asd");
        ArticleRequestDto articleRequestDto = new ArticleRequestDto("hello", "bonjour");

        when(userInternalService.findByUserId(userContext.getUserId())).thenReturn(user);

        Article article = articleInternalService.save(articleRequestDto, userContext);

        assertThat(article.getTitle()).isEqualTo("hello");
        assertThat(article.getContent()).isEqualTo("bonjour");
    }

    @Test
    void 게시글_조회_테스트() {
        String title = "title";
        String content = "content";
        User user = new User("asd", "asd");
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
        User user = new User("asd", "asd");
        Article article = new Article(title, content, user);
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> articleInternalService.findById(1L));
    }

    @Test
    void 게시글_전체_조회_테스트() {
        List<Article> articles = createArticles(10);
        when(articleRepository.findAllByOrderByIdDesc()).thenReturn(articles);

        List<Article> actualArticles = articleInternalService.findAll();

        assertThat(actualArticles.size()).isEqualTo(10);
    }

    @Test
    void 게시글_업데이트_작성자일_경우_테스트() {
        String title = "title";
        String content = "content";
        String updateTitle = "title1";
        String updateContent = "content1";

        UserContext userContext = new UserContext("asd", "asd");
        User user = new User("asd", "asd");
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

        UserContext userContext = new UserContext("asd", "asd");
        User user1 = new User("asd", "asd");
        FieldSetter.set(user1, "id", 1L);

        User user2 = new User("asd", "asd");
        FieldSetter.set(user2, "id", 2L);

        Article article = new Article(title, content, user1);
        ArticleUpdateDto articleUpdateDto = new ArticleUpdateDto(1L, updateTitle, updateContent);
        when(userInternalService.findById(userContext.getId())).thenReturn(user2);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        assertThrows(IllegalArgumentException.class, () -> articleInternalService.update(articleUpdateDto, userContext));
    }

    private List<Article> createArticles(int numberOfArticle) {
        User user = new User("userId", "url");
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < numberOfArticle; i++) {
            articles.add(new Article(String.valueOf(i), String.valueOf(i), user));
        }
        return articles;
    }
}