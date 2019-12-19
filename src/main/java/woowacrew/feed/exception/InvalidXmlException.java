package woowacrew.feed.exception;

public class InvalidXmlException extends RuntimeException {
    public InvalidXmlException() {
        super("올바르지 않은 XML형식 입니다.");
    }
}
