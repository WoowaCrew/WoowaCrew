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
import woowacrew.article.crew.domain.CrewArticleRepository;
import woowacrew.article.crew.exception.CrewArticleAccessDenyException;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleUpdateDto;
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.article.free.exception.MisMatchUserException;
import woowacrew.article.free.exception.NotFoundArticleException;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.common.service.FieldSetter;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrewArticleInternalServiceTest {
    @Mock
    private CrewArticleRepository crewArticleRepository;
    @Mock
    private UserInternalService userInternalService;

    @InjectMocks
    private CrewArticleInternalService crewArticleInternalService;

    @Test
    void 게시글_생성_테스트() {
        UserContext userContext = new UserContext(1L, "asd", "asdf", UserRole.ROLE_CREW);
        User user = new User("asd", new Degree());
        ArticleRequestDto articleRequestDto = new ArticleRequestDto("hello", "bonjour");
        CrewArticle expectedCrewArticle = new CrewArticle(articleRequestDto.getTitle(), articleRequestDto.getContent(), user);

        when(userInternalService.findById(userContext.getId())).thenReturn(user);
        when(crewArticleRepository.save(any(CrewArticle.class))).thenReturn(expectedCrewArticle);
        CrewArticle article = crewArticleInternalService.save(articleRequestDto, userContext);

        assertThat(article.getTitle()).isEqualTo("hello");
        assertThat(article.getContent()).isEqualTo("bonjour");
    }

    @Test
    void 게시글_조회_테스트() {
        String title = "title";
        String content = "content";
        String oauthId = "oauthId";
        UserContext userContext = new UserContext(oauthId);
        User user = new User(oauthId, new Degree());
        CrewArticle article = new CrewArticle(title, content, user);
        when(userInternalService.findById(any())).thenReturn(user);
        when(crewArticleRepository.findById(1L)).thenReturn(Optional.of(article));

        CrewArticle expectedArticle = crewArticleInternalService.findById(1L, userContext);
        assertThat(expectedArticle.getTitle()).isEqualTo(title);
        assertThat(expectedArticle.getContent()).isEqualTo("content");
    }

    @Test
    void 없는_게시글_조회_테스트() {
        String oauthId = "oauthId";
        UserContext userContext = new UserContext(oauthId);
        User user = new User(oauthId, new Degree());
        when(userInternalService.findById(any())).thenReturn(user);
        when(crewArticleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundArticleException.class, () -> crewArticleInternalService.findById(1L, userContext));
    }

    @Test
    void 기수가_일치하지않는_게시글_접근시_에러가_발생한다() {
        String title = "title";
        String content = "content";
        String oauthId = "oauthId";
        UserContext userContext = new UserContext(oauthId);
        Degree degree = new Degree();
        FieldSetter.set(degree, "id", 1L);
        Degree degree1 = new Degree();
        FieldSetter.set(degree1, "id", 0L);
        User user = new User(oauthId, degree);
        User requestUser = new User("oauthId2", degree1);
        CrewArticle article = new CrewArticle(title, content, user);
        when(userInternalService.findById(any())).thenReturn(requestUser);
        when(crewArticleRepository.findById(1L)).thenReturn(Optional.of(article));

        assertThrows(CrewArticleAccessDenyException.class, () -> crewArticleInternalService.findById(1L, userContext));


    }

    @Test
    void 게시글_전체_조회_테스트() {
        List<CrewArticle> articles = createArticles(CrewArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE);
        Pageable pageable = PageRequest.of(1, ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE);
        UserContext userContext = new UserContext(1L, "van", "nickname", UserRole.ROLE_CREW);

        when(userInternalService.findById(userContext.getId())).thenReturn(new User(userContext.getOauthId(), new Degree()));
        when(crewArticleRepository.findAllByBasicArticleFormAuthorDegree(any(Degree.class), any(Pageable.class))).thenReturn(new PageImpl<>(articles));

        Page<CrewArticle> actualArticles = crewArticleInternalService.findAllByCrew(userContext, pageable);

        assertThat(actualArticles.getTotalElements()).isEqualTo(ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE);
    }

    @Test
    void 게시글_페이징_사이즈가_20이_아니면_에러가_발생한다() {
        Pageable pageable = PageRequest.of(1, 5);

        assertThatThrownBy(() -> crewArticleInternalService.findAllByCrew(new UserContext("oauthId"), pageable)).isInstanceOf(InvalidPageRequstException.class);
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

        CrewArticle article = new CrewArticle(title, content, user);
        ArticleUpdateDto articleUpdateDto = new ArticleUpdateDto(1L, updateTitle, updateContent);
        when(userInternalService.findById(userContext.getId())).thenReturn(user);
        when(crewArticleRepository.findById(1L)).thenReturn(Optional.of(article));

        CrewArticle updateArticle = crewArticleInternalService.update(articleUpdateDto, userContext);

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

        CrewArticle article = new CrewArticle(title, content, user1);
        ArticleUpdateDto articleUpdateDto = new ArticleUpdateDto(1L, updateTitle, updateContent);
        when(userInternalService.findById(userContext.getId())).thenReturn(user2);
        when(crewArticleRepository.findById(1L)).thenReturn(Optional.of(article));

        assertThrows(MisMatchUserException.class, () -> crewArticleInternalService.update(articleUpdateDto, userContext));
    }

    private List<CrewArticle> createArticles(int numberOfArticle) {
        User user = new User("userId", new Degree());
        List<CrewArticle> articles = new ArrayList<>();
        for (int i = 0; i < numberOfArticle; i++) {
            articles.add(new CrewArticle(String.valueOf(i), String.valueOf(i), user));
        }
        return articles;
    }
}