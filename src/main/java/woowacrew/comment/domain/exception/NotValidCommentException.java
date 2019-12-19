package woowacrew.comment.domain.exception;

public class NotValidCommentException extends RuntimeException {
    public NotValidCommentException() {
        super("유효하지 않은 comment");
    }
}
