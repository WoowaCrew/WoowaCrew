package woowacrew.article.anonymous.domain;

import woowacrew.article.anonymous.exception.InvalidPasswordException;

import javax.persistence.Embeddable;

@Embeddable
public class AnonymousArticlePassword {

    private static final int MIN_LENGTH = 8;

    private String password;

    private AnonymousArticlePassword() {
    }

    public AnonymousArticlePassword(String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        if (password == null || password.length() < MIN_LENGTH) {
            throw new InvalidPasswordException();
        }
    }

    public boolean match(String password) {
        return this.password.equals(password);
    }
}
