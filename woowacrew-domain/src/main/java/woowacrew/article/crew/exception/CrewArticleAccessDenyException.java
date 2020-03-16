package woowacrew.article.crew.exception;

public class CrewArticleAccessDenyException extends RuntimeException{
    public CrewArticleAccessDenyException() {
        super("해당 게시글에 접근할 권이 없습니다.");
    }
}
