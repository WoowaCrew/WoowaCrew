package woowacrew.article;

import org.junit.jupiter.api.Test;
import woowacrew.article.free.exception.MisMatchUserException;
import woowacrew.common.service.FieldSetter;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicArticleFormTest {

    @Test
    void 생성_테스트() {
        String title = "title";
        String content = "content";
        String oauthId = "oauthId";
        User author = new User(oauthId, new Degree());
        BasicArticleForm basicArticleForm = new BasicArticleForm(title, content, author);

        assertThat(basicArticleForm.getTitle()).isEqualTo(title);
        assertThat(basicArticleForm.getContent()).isEqualTo(content);
        assertThat(basicArticleForm.getAuthor().getOauthId()).isEqualTo(oauthId);
    }

    @Test
    void 같은_유저가_아니라면_에러를_발생한다() {
        User user = new User("vsh123", new Degree());
        User user2 = new User("hyunssooo", new Degree());
        FieldSetter.set(user, "id", 1L);
        FieldSetter.set(user2, "id", 2L);
        BasicArticleForm basicArticleForm = new BasicArticleForm("title", "content", user);

        assertThrows(MisMatchUserException.class, () -> basicArticleForm.checkAuthor(user2));
    }
}