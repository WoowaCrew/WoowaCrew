package woowacrew.github.dto;

public class UserCommitRankAndPointDto {

    private int rank;
    private int point;

    public UserCommitRankAndPointDto(int rank, int point) {
        this.rank = rank;
        this.point = point;
    }

    public int getRank() {
        return rank;
    }

    public int getPoint() {
        return point;
    }
}
