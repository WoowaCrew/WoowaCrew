package woowacrew.feed.exception;

public class NotFoundFeedSourceException extends RuntimeException {
    public NotFoundFeedSourceException() {
        super("요청하신 피드를 찾을 수 없습니다.");
    }
}
