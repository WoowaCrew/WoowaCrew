package woowacrew.search.exception;

public class InvalidFieldPathException extends RuntimeException {
    public InvalidFieldPathException() {
        super("유효하지 않은 필드 경로입니다.");
    }
}
