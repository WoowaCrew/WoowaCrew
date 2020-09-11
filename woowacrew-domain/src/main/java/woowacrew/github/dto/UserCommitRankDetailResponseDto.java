package woowacrew.github.dto;

public class UserCommitRankDetailResponseDto {

    private int rank;
    private int point;
    private int degree;
    private String githubId;
    private String nickname;

    private UserCommitRankDetailResponseDto() {
    }

    public UserCommitRankDetailResponseDto(int point, int degree, String githubId, String nickname) {
        this.point = point;
        this.degree = degree;
        this.githubId = githubId;
        this.nickname = nickname;
    }

    public UserCommitRankDetailResponseDto(int rank, int point, int degree, String githubId, String nickname) {
        this.rank = rank;
        this.point = point;
        this.degree = degree;
        this.githubId = githubId;
        this.nickname = nickname;
    }

    public int getRank() {
        return rank;
    }

    public int getPoint() {
        return point;
    }

    public int getDegree() {
        return degree;
    }

    public String getGithubId() {
        return githubId;
    }

    public String getNickname() {
        return nickname;
    }
}
