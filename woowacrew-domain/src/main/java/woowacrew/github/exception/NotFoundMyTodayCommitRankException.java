package woowacrew.github.exception;

public class NotFoundMyTodayCommitRankException extends RuntimeException {
    public NotFoundMyTodayCommitRankException() {
        super("오늘 나의 커밋 랭킹을 찾을 수 없습니다.");
    }
}
