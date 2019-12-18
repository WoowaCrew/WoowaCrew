package woowacrew.article.anonymous.exception;

public class InvalidPasswordException extends RuntimeException {
    private static final String MESSAGE = "잘못된 비밀번호 형식입니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}
