package woowacrew.article.anonymous.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacrew.article.anonymous.exception.MismatchPasswordException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnonymousArticleTest {
    @Test
    @DisplayName("익명게시판 게시글이 미승인 상태로 생성된다.")
    void createUnapproved() {
        AnonymousArticle anonymousArticle =
                new AnonymousArticle("title", "content", "password");

        assertThat(anonymousArticle.isApproved()).isFalse();
    }

    @Test
    @DisplayName("익명게시판 게시글을 승인하면 승인 상태로 생성된다.")
    void create() {
        AnonymousArticle anonymousArticle =
                new AnonymousArticle("title", "content", "password");

        anonymousArticle.approve();

        assertThat(anonymousArticle.isApproved()).isTrue();
    }

    @Test
    @DisplayName("비밀번호 일치시 게시글 수정이 가능하다.")
    void updateWithMatchPassword() {
        AnonymousArticle anonymousArticle =
                new AnonymousArticle("title", "content", "password");

        anonymousArticle.update("new title", "new content", "password");

        assertThat(anonymousArticle.getTitle()).isEqualTo("new title");
        assertThat(anonymousArticle.getContent()).isEqualTo("new content");
    }

    @Test
    @DisplayName("비밀번호 불일치시 게시글 수정이 불가능하다.")
    void updateWithMisMatchPassword() {
        AnonymousArticle anonymousArticle =
                new AnonymousArticle("title", "content", "password");

        assertThrows(MismatchPasswordException.class,
                () -> anonymousArticle.update("new title", "new content", "invalid password"));

        assertThat(anonymousArticle.getTitle()).isEqualTo("title");
        assertThat(anonymousArticle.getContent()).isEqualTo("content");
    }
}