package woowacrew.user.service.exception;

public class NotExistUserException extends RuntimeException {
    public NotExistUserException() {
        super("존재하지 않는 유저 입니다.");
    }
}
