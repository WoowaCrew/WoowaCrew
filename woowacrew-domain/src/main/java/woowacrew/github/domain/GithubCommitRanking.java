package woowacrew.github.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import woowacrew.github.dto.UserCommitRankDetailResponseDto;
import woowacrew.github.exception.CommitRankBoundaryException;

import java.util.*;

public class GithubCommitRanking {

    private static final int MIN_RANK = 1;
    private static final int MAX_RANK = 50;
    private static final int MAX_RANK_COUNT = 10;
    private static final Set<Integer> VALID_START_RANK = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(1, 11, 21, 31, 41)));
    private static final Logger log = LoggerFactory.getLogger(GithubCommitRanking.class);

    private List<UserCommitRankDetailResponseDto> ranking;
    private boolean maxRank;

    private GithubCommitRanking() {
    }

    private GithubCommitRanking(List<UserCommitRankDetailResponseDto> ranking, boolean maxRank) {
        this.ranking = ranking;
        this.maxRank = maxRank;
    }

    public static GithubCommitRanking of(List<UserCommitRankDetailResponseDto> totalCommitRank, int startRank) {
        int maxRank = totalCommitRank.size();
        int endRank = getEndRank(startRank, maxRank);
        List<UserCommitRankDetailResponseDto> ranking = getRankingFromStartRankToEndRank(totalCommitRank, startRank, endRank);
        boolean isMaxRank = (endRank == maxRank);
        return new GithubCommitRanking(ranking, isMaxRank);
    }

    private static int getEndRank(int startRank, int maxRank) {
        checkMaxRank(maxRank);
        checkStartRank(startRank, maxRank);
        int startRankIndex = startRank - 1;
        int endRank = startRankIndex + MAX_RANK_COUNT;
        if (endRank > maxRank) {
            endRank = maxRank;
        }
        return endRank;
    }

    private static void checkMaxRank(int maxRank) {
        if (maxRank < MIN_RANK || maxRank > MAX_RANK) {
            throw new CommitRankBoundaryException();
        }
    }

    private static void checkStartRank(int startRank, int maxRank) {
        if (!VALID_START_RANK.contains(startRank) || startRank > maxRank) {
            throw new CommitRankBoundaryException();
        }
    }

    private static List<UserCommitRankDetailResponseDto> getRankingFromStartRankToEndRank(List<UserCommitRankDetailResponseDto> totalCommitRank, int startRank, int endRank) {
        try {
            int startRankIndex = startRank - 1;
            return totalCommitRank.subList(startRankIndex, endRank);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CommitRankBoundaryException();
        }
    }

    public List<UserCommitRankDetailResponseDto> getRanking() {
        return this.ranking;
    }

    public boolean isMaxRank() {
        return this.maxRank;
    }
}
