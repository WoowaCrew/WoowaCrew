package woowacrew.feed.exception;

public class FeedSourceUpdateException extends RuntimeException {
    public FeedSourceUpdateException() {
        super("피드 업데이트에 실패였습니다.");
    }
}
