package woowacrew.github.dto;

public class ThisMonthCommitRankRequestDto {

    private int year;
    private int month;

    public ThisMonthCommitRankRequestDto(int year, int month) {
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
