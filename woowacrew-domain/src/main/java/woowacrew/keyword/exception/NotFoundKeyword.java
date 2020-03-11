package woowacrew.keyword.exception;

public class NotFoundKeyword extends RuntimeException {
    public NotFoundKeyword() {
        super("해당 검색어를 찾을 수 없습니다.");
    }
}
