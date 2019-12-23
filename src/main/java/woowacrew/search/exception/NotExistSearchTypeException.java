package woowacrew.search.exception;

public class NotExistSearchTypeException extends RuntimeException {
    public NotExistSearchTypeException() {
        super("존재하지 않는 검색 타입 입니다.");
    }
}
