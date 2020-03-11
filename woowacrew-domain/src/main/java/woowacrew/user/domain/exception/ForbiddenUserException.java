package woowacrew.user.domain.exception;

public class ForbiddenUserException extends RuntimeException {
    public ForbiddenUserException() {
        super("권한이 없는 유저입니다.");
    }
}
