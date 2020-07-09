package woowacrew.github.dto;

public class GithubCommitRequestDto {

    private int year;
    private int month;

    public GithubCommitRequestDto(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
