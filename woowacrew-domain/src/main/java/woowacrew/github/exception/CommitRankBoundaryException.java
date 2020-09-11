package woowacrew.github.exception;

public class CommitRankBoundaryException extends RuntimeException {
    public CommitRankBoundaryException() {
        super("커밋 랭킹 범위에서 벗어났습니다.");
    }
}
