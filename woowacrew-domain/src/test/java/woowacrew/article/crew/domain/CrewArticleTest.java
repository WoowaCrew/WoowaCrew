package woowacrew.article.crew.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import woowacrew.article.free.exception.MisMatchUserException;
import woowacrew.common.service.FieldSetter;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CrewArticleTest {
    private User user;
    private User user2;

    @BeforeEach
    void setUp() {
        user = new User("vsh123", new Degree());
        user2 = new User("hyunssooo", new Degree());
        FieldSetter.set(user, "id", 1L);
    }

    @Test
    void 업데이트_작성자일_경우_테스트() {
        CrewArticle article = new CrewArticle("test", "testContent", user);
        article.update(user, "updateTitle", "updateContent");

        assertThat(article.getTitle()).isEqualTo("updateTitle");
        assertThat(article.getContent()).isEqualTo("updateContent");
    }

    @Test
    void 업데이트_작성자가_아닐_경우_테스트() {
        CrewArticle article = new CrewArticle("test", "testContent", user);
        FieldSetter.set(user2, "id", 2L);

        assertThrows(MisMatchUserException.class, () -> article.update(user2, "updateTitle", "updateContent"));
    }

    @Test
    void 작성자가_아닐_경우_테스트() {
        CrewArticle article = new CrewArticle("test", "testContent", user);
        FieldSetter.set(user2, "id", 2L);

        assertThrows(MisMatchUserException.class, () -> article.checkAuthor(user2));
    }

    @Test
    void 게시글의_작성자와_요청자의_기수가_같은지_확인한다() {
        CrewArticle article = new CrewArticle("test", "testContent", user);

        assertThat(article.isAccessible(user)).isTrue();
    }
}