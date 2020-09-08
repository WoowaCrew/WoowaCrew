package woowacrew.github.domain;

import org.junit.jupiter.api.Test;
import woowacrew.github.dto.UserCommitRankDetailResponseDto;
import woowacrew.github.exception.CommitRankBoundaryException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GithubCommitRankingTest {

    @Test
    void 정상적으로_GithubCommitRank를_생성한다() {
        List<UserCommitRankDetailResponseDto> totalCommitRank = generateTotalCommitRank(50);
        for (int startRank : Arrays.asList(1, 11, 21, 31, 41)) {
            GithubCommitRanking githubCommitRanking = GithubCommitRanking.of(totalCommitRank, startRank);

            assertThat(githubCommitRanking.getRanking().size()).isEqualTo(10);
            if (startRank == 41) assertTrue(githubCommitRanking.isMaxRank());
            else assertFalse(githubCommitRanking.isMaxRank());
        }
    }

    @Test
    void 존재하지_않는_시작점이면_예외가_발생한다() {
        List<UserCommitRankDetailResponseDto> totalCommitRank = generateTotalCommitRank(10);
        assertThrows(CommitRankBoundaryException.class, () -> GithubCommitRanking.of(totalCommitRank, 2));
    }

    @Test
    void 전체랭킹이_50개를_초과한_경우_예외가_발생한다() {
        List<UserCommitRankDetailResponseDto> totalCommitRank = generateTotalCommitRank(51);
        assertThrows(CommitRankBoundaryException.class, () -> GithubCommitRanking.of(totalCommitRank, 1));
    }

    private List<UserCommitRankDetailResponseDto> generateTotalCommitRank(int maxSize) {
        List<UserCommitRankDetailResponseDto> result = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            result.add(new UserCommitRankDetailResponseDto(1, 1, "githubId", "nickname"));
        }
        return result;
    }
}