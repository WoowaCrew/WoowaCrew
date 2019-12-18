package woowacrew.article;

import org.junit.jupiter.api.Test;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

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
}