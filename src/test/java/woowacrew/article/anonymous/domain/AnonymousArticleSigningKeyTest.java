package woowacrew.article.anonymous.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacrew.article.anonymous.exception.InvalidSigningKeyException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AnonymousArticleSigningKeyTest {
    @Test
    @DisplayName("비밀번호가 null인 경우 예외 발생")
    void nullPassword() {
        assertThrows(InvalidSigningKeyException.class,
                () -> new AnonymousArticleSigningKey(null));
    }

    @Test
    @DisplayName("비밀번호가 8자리 이하인 경우 예외 발생")
    void passwordLessThanMinLength() {
        assertThrows(InvalidSigningKeyException.class,
                () -> new AnonymousArticleSigningKey("1234567"));
    }
}