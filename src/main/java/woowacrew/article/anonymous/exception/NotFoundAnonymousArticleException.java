package woowacrew.article.anonymous.exception;

public class NotFoundAnonymousArticleException extends RuntimeException {
    private static final String MESSAGE = "익명 게시글을 찾을 수 없습니다.";

    public NotFoundAnonymousArticleException() {
        super(MESSAGE);
    }
}
