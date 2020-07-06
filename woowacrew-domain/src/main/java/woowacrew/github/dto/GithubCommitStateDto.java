package woowacrew.github.dto;

import java.time.LocalDate;

public class GithubCommitStateDto {

    private LocalDate date;
    private int commitCount;

    private GithubCommitStateDto() {
    }

    public GithubCommitStateDto(LocalDate date, int commitCount) {
        this.date = date;
        this.commitCount = commitCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCommitCount() {
        return commitCount;
    }
}
