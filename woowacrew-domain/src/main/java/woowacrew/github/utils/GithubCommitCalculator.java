package woowacrew.github.utils;

import woowacrew.github.dto.GithubCommitStateDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. 커밋 개수 1개마다 10점을 부여한다.
 * 2. 매일 커밋할 때마다 x2, x4, x8 까지 곱한다.
 * 3. 커밋이 없다면 다시 x2부터 시작한다.
 * ex) x2, x4, x8, x8, x8, x2, x4 ...
 */
public class GithubCommitCalculator {

    private static final int MIN_BONUS_POINT = 0;
    private static final int MAX_BONUS_POINT = 3;
    private static final int DEFAULT_BASE = 2;
    private static final int DEFAULT_MULTIPLIER = 10;

    private GithubCommitCalculator() {
    }

    public static int calculate(List<GithubCommitStateDto> commitState) {
        AtomicInteger bonusPoint = new AtomicInteger(MIN_BONUS_POINT);
        return commitState.stream()
                .map(GithubCommitStateDto::getCommitCount)
                .mapToInt(commitCount -> {
                    bonusPoint.set(getBonusPoint(commitCount, bonusPoint.get()));
                    return calculate(commitCount, bonusPoint.get());
                })
                .sum();
    }

    private static int calculate(int commitCount, int bonusPoint) {
        bonusPoint = (int) Math.pow(DEFAULT_BASE, bonusPoint);
        return (commitCount * DEFAULT_MULTIPLIER) * bonusPoint;
    }

    private static int getBonusPoint(int commitCount, int bonusPoint) {
        if (isEmptyCommit(commitCount)) {
            return MIN_BONUS_POINT;
        }
        return bonusPoint == MAX_BONUS_POINT ? bonusPoint : bonusPoint + 1;
    }

    private static boolean isEmptyCommit(int commitCount) {
        return commitCount == 0;
    }
}
