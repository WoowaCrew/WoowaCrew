package woowacrew.article.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import woowacrew.common.service.FieldSetter;
import woowacrew.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleTest {
    private User user;
    private User user2;

    @BeforeEach
    void setUp() {
        user = new User("vsh123", "vsh123");
        user2 = new User("hyunssooo", "hyunssooo");
        FieldSetter.set(user, "id", 1L);
    }

    @Test
    void 업데이트_작성자일_경우_테스트() {
        Article article = new Article("test", "testContent", user);
        article.update(user, "updateTitle", "updateContent");

        assertThat(article.getTitle()).isEqualTo("updateTitle");
        assertThat(article.getContent()).isEqualTo("updateContent");
    }

    @Test
    void 업데이트_작성자가_아닐_경우_테스트() {
        Article article = new Article("test", "testContent", user);
        FieldSetter.set(user2, "id", 2L);

       assertThrows(IllegalArgumentException.class, () -> article.update(user2, "updateTitle", "updateContent"));
    }
}