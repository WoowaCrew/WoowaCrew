package woowacrew.article.anonymous.exception;

public class MismatchPasswordException extends RuntimeException {
    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public MismatchPasswordException() {
        super(MESSAGE);
    }
}
