package woowacrew.github.dto;

import java.util.List;

public class TotalCommitRankRequestDto {

    private List<UserCommitRankDetailResponseDto> totalCommitRank;
    private int startRank;

    private TotalCommitRankRequestDto() {
    }

    public TotalCommitRankRequestDto(List<UserCommitRankDetailResponseDto> totalCommitRank, int startRank) {
        this.totalCommitRank = totalCommitRank;
        this.startRank = startRank;
    }

    public List<UserCommitRankDetailResponseDto> getTotalCommitRank() {
        return totalCommitRank;
    }

    public int getStartRank() {
        return startRank;
    }
}
