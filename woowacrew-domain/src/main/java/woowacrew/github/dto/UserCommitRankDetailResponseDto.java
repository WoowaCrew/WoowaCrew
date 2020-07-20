package woowacrew.github.dto;

public class UserCommitRankDetailResponseDto {

    private int rank;
    private int degree;
    private int point;
    private String githubId;
    private String nickname;

    private UserCommitRankDetailResponseDto() {
    }

    public static UserCommitRankDetailResponseDto of(UserCommitRankAndPointDto userCommitRankAndPointDto, int degree, String githubId, String nickname) {
        UserCommitRankDetailResponseDto result = new UserCommitRankDetailResponseDto();
        result.rank = userCommitRankAndPointDto.getRank();
        result.degree = degree;
        result.point = userCommitRankAndPointDto.getPoint();
        result.githubId = githubId;
        result.nickname = nickname;
        return result;
    }

    public int getRank() {
        return rank;
    }

    public int getDegree() {
        return degree;
    }

    public int getPoint() {
        return point;
    }

    public String getGithubId() {
        return githubId;
    }

    public String getNickname() {
        return nickname;
    }
}
