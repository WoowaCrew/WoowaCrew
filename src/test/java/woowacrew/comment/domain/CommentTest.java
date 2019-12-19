package woowacrew.comment.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.exception.MisMatchUserException;
import woowacrew.comment.domain.exception.NotValidCommentException;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommentTest {

    @Mock
    private User mockUser;

    @Mock
    private Article mockArticle;

    private User author = new User("zino", new Degree());
    private Article article = new Article("title", "content", author);

    @Test
    void 생성_정상_테스트() {
        assertDoesNotThrow(() -> new Comment(author, "testContent", article));
    }

    @Test
    void 생성_유저가_null일_때_테스트() {
        assertThrows(NotValidCommentException.class, () -> new Comment(mockUser, "content", article));
    }

    @Test
    void 생성_article이_null일_때_테스트() {
        assertThrows(NotValidCommentException.class, () -> new Comment(author, "content", mockArticle));
    }

    @Test
    void 생성_comment_내용이_공백일때_테스트() {
        assertThrows(NotValidCommentException.class, () -> new Comment(author, "", article));
    }

    @Test
    void 업데이트_작성자가_동일할_경우_테스트() {
        Comment comment = new Comment(author, "test1 content", article);
        comment.update(author, "update comment");

        assertThat(comment.getContent()).isEqualTo("update comment");
    }

    @Test
    void 업데이트_작성자가_동일하지_않는_경우_테스트() {
        Comment comment = new Comment(author, "test1 content", article);

        assertThrows(MisMatchUserException.class, () -> comment.update(mockUser, "update comment"));
    }
}