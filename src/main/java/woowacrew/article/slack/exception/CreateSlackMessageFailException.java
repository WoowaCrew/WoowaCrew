package woowacrew.article.slack.exception;

public class CreateSlackMessageFailException extends RuntimeException {
    public CreateSlackMessageFailException() {
        super("슬랙 메세지를 저장하는데 실패하였습니다.");
    }
}
