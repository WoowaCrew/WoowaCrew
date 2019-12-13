package woowacrew.article.free.exception;

public class InvalidPageRequstException extends RuntimeException {
    public InvalidPageRequstException() {
        super("페이지 사이즈는 수정이 불가합니다.");
    }
}
