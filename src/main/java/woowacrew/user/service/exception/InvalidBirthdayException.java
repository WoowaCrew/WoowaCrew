package woowacrew.user.service.exception;

public class InvalidBirthdayException extends RuntimeException {

    public InvalidBirthdayException(Throwable e) {
        super("올바르지 않은 생년월일 입니다.", e);
    }
}
