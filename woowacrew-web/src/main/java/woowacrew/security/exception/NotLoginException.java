package woowacrew.security.exception;

public class NotLoginException extends RuntimeException {
    public NotLoginException() {
        super("유저가 로그인되어 있지 않습니다.");
    }
}
