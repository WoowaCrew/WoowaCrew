package woowacrew.user.service.exception;

public class InvalidBirthdayException extends RuntimeException {
    public static final String ERROR_MESSAGE = "올바르지 않은 생년월일 입니다.";

    public InvalidBirthdayException(Throwable e) {
        super(ERROR_MESSAGE, e);
    }
}
