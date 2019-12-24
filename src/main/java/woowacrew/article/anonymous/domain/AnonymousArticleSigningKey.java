package woowacrew.article.anonymous.domain;

import woowacrew.article.anonymous.exception.InvalidSigningKeyException;

import javax.persistence.Embeddable;

@Embeddable
public class AnonymousArticleSigningKey {

    private static final int MIN_LENGTH = 8;

    private String signingKey;

    private AnonymousArticleSigningKey() {
    }

    public AnonymousArticleSigningKey(String signingKey) {
        validate(signingKey);
        this.signingKey = signingKey;
    }

    private void validate(String signingKey) {
        if (signingKey == null || signingKey.length() < MIN_LENGTH) {
            throw new InvalidSigningKeyException();
        }
    }

    public boolean match(String signingKey) {
        return this.signingKey.equals(signingKey);
    }
}
