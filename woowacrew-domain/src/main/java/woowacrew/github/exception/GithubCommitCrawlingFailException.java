package woowacrew.github.exception;

public class GithubCommitCrawlingFailException extends RuntimeException {
    public GithubCommitCrawlingFailException() {
        super("Github 커밋 정보를 크롤링하는데 실패하였습니다.");
    }
}
