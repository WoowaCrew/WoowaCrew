package woowacrew.feed.exception;

public class AlreadyExistSourceUrlException extends RuntimeException {
    public AlreadyExistSourceUrlException() {
        super("이미 존재하는 Url입니다.");
    }
}
