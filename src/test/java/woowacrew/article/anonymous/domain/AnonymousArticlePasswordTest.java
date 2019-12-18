package woowacrew.article.anonymous.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacrew.article.anonymous.exception.InvalidPasswordException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AnonymousArticlePasswordTest {
    @Test
    @DisplayName("비밀번호가 null인 경우 예외 발생")
    void nullPassword() {
        assertThrows(InvalidPasswordException.class,
                () -> new AnonymousArticlePassword(null));
    }

    @Test
    @DisplayName("비밀번호가 8자리 이하인 경우 예외 발생")
    void passwordLessThanMinLength() {
        assertThrows(InvalidPasswordException.class,
                () -> new AnonymousArticlePassword("1234567"));
    }
}