package woowacrew.search.exception;

public class SendSlackBirthdayMessageFailException extends RuntimeException {
    public SendSlackBirthdayMessageFailException() {
        super("생일 메세지를 보내는데 실패 했습니다.");
    }
}
