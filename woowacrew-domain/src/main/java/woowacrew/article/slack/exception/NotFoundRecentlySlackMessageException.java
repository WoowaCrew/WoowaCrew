package woowacrew.article.slack.exception;

public class NotFoundRecentlySlackMessageException extends RuntimeException {
    public NotFoundRecentlySlackMessageException() {
        super("최근 슬랙 메세지가 존재하지 않습니다.");
    }
}
