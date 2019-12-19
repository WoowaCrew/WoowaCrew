package woowacrew.comment.service.exception;

public class NotFoundCommentException extends RuntimeException {
    public NotFoundCommentException() {
        super("comment not found");
    }
}
