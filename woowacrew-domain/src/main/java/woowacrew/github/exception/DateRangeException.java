package woowacrew.github.exception;

public class DateRangeException extends RuntimeException {
    public DateRangeException() {
        super("해당 날짜는 범위에 벗어난 날짜 입니다.");
    }
}
