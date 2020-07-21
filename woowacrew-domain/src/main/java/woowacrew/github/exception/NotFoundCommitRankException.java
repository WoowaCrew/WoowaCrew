package woowacrew.github.exception;

public class NotFoundCommitRankException extends RuntimeException {
    public NotFoundCommitRankException() {
        super("해당 유저의 커밋 순위를 찾을 수 없습니다.");
    }
}
