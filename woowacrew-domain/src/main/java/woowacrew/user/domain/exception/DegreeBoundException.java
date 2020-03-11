package woowacrew.user.domain.exception;

public class DegreeBoundException extends RuntimeException {
    public DegreeBoundException() {
        super("올바르지 않은 기수 입니다.");
    }
}
