package woowacrew.search.exception;

public class NotMatchArticleSearchKeyException extends RuntimeException {
    public NotMatchArticleSearchKeyException() {
        super("올바르지 않은 검색 타입 입니다.");
    }
}
