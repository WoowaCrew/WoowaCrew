package woowacrew.article.free.exception;

public class NotFoundArticleException extends RuntimeException {
    public NotFoundArticleException() {
        super("요청하신 게시글을 찾을 수 없습니다.");
    }
}
