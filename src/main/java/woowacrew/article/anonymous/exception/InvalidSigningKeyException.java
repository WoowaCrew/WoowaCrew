package woowacrew.article.anonymous.exception;

public class InvalidSigningKeyException extends RuntimeException {
    private static final String MESSAGE = "잘못된 비밀번호 형식입니다.";

    public InvalidSigningKeyException() {
        super(MESSAGE);
    }
}
