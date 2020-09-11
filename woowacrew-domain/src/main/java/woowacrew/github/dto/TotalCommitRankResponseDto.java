package woowacrew.github.dto;

import java.util.List;

public class TotalCommitRankResponseDto {

    private List<UserCommitRankDetailResponseDto> commitRank;
    private boolean isMaxRank;

    private TotalCommitRankResponseDto() {
    }

    public TotalCommitRankResponseDto(List<UserCommitRankDetailResponseDto> commitRank, boolean isMaxRank) {
        this.commitRank = commitRank;
        this.isMaxRank = isMaxRank;
    }

    public List<UserCommitRankDetailResponseDto> getCommitRank() {
        return commitRank;
    }

    public boolean isMaxRank() {
        return isMaxRank;
    }
}
