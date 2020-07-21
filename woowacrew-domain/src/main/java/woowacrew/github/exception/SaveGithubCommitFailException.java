package woowacrew.github.exception;

public class SaveGithubCommitFailException extends RuntimeException {
    public SaveGithubCommitFailException() {
        super("깃헙 커밋 정보를 저장하는데 실패하였습니다.");
    }
}
