package woowacrew.github.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import woowacrew.github.dto.GithubCommitStateDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GithubCommitCalculatorTest {

    private List<GithubCommitStateDto> commitStateResult;

    @BeforeEach
    void setUp() {
        this.commitStateResult = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GithubCommitStateDto githubCommitStateDto = new GithubCommitStateDto(LocalDate.now(), 1);
            this.commitStateResult.add(githubCommitStateDto);
        }
    }

    @Test
    void 정상적으로_커밋_점수를_계산한다() {
        int calculate = GithubCommitCalculator.calculate(commitStateResult);

        assertThat(calculate).isEqualTo(700);
    }
}