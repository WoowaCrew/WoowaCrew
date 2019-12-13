package woowacrew.article.free.exception;

public class MisMatchUserException extends RuntimeException {
    public MisMatchUserException() {
        super("유저가 일치하지 않습니다.");
    }
}
