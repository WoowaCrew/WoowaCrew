package woowacrew.article.slack.exception;

public class NotFoundSlackMessageException extends RuntimeException {
    public NotFoundSlackMessageException() {
        super("존재하지 않는 슬랙 메세지 입니다.");
    }
}
