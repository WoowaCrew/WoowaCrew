package woowacrew.github.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.degree.domain.Degree;
import woowacrew.github.domain.GithubCommit;
import woowacrew.github.dto.*;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GithubCommitServiceTest {

    @Mock
    private GithubCommitInternalService githubCommitInternalService;

    @Mock
    private UserInternalService userInternalService;

    @InjectMocks
    private GithubCommitService githubCommitService;

    @Test
    void 정상적으로_커밋_정보들을_저장한다() {
        githubCommitService.save(new ThisMonthCommitRankRequestDto(2020, 6));

        verify(userInternalService, times(1)).findByGithubIdIsNotNull();
        verify(githubCommitInternalService, times(1)).save(anyList(), any(LocalDate.class));
    }

    @Test
    void 정상적으로_나의_커밋_랭킹_정보를_가져온다() {
        User user = new User("oauthId", "githubId", new Degree());
        user.updateUserInfo("hyo", LocalDate.of(1995, 6, 8));

        UserContext userContext = new UserContext(1L, user.getOauthId(), user.getNickname(), UserRole.ROLE_PRECOURSE);

        when(userInternalService.findById(anyLong())).thenReturn(user);
        when(githubCommitInternalService.getCommitRankByUser(user)).thenReturn(new UserCommitRankAndPointDto(1, 200));

        UserCommitRankDetailResponseDto result = githubCommitService.getLoginUserCommitRank(userContext);

        assertNotNull(result);
        assertThat(result.getRank()).isEqualTo(1);
        assertThat(result.getDegree()).isEqualTo(0);
        assertThat(result.getPoint()).isEqualTo(200);
        assertThat(result.getGithubId()).isEqualTo("githubId");
        assertThat(result.getNickname()).isEqualTo("hyo");
    }

    @Test
    void 전체_커밋_랭킹을_가져온다() {
        int count = 3;
        List<GithubCommit> githubCommits = getGithubCommits(count);

        when(githubCommitInternalService.getTotalCommitRank(any(LocalDate.class))).thenReturn(githubCommits);

        List<UserCommitRankDetailResponseDto> result = githubCommitService.getTotalCommitRank();

        assertNotNull(result);
        assertThat(result.size()).isEqualTo(count);
    }

    private List<GithubCommit> getGithubCommits(int count) {
        List<GithubCommit> result = new ArrayList<>();
        GithubCommit mockGithubCommit = mock(GithubCommit.class);
        User mockUser = mock(User.class);
        Degree degree = mock(Degree.class);
        for (int i = 0; i < count; i++) {
            result.add(mockGithubCommit);
        }
        when(mockGithubCommit.getPoint()).thenReturn(100);
        when(mockGithubCommit.getUser()).thenReturn(mockUser);
        when(mockUser.getGithubId()).thenReturn("githubId");
        when(mockUser.getDegree()).thenReturn(degree);
        when(mockUser.getNickname()).thenReturn("nickname");
        when(degree.getDegreeNumber()).thenReturn(1);
        return result;
    }

    @Test
    void 전체_랭킹에서_1위에서_10위까지_가져온다() {
        List<UserCommitRankDetailResponseDto> totalCommitRank = generateTotalCommitRank(20);
        int startRank = 1;
        int endRank = 10;
        TotalCommitRankRequestDto totalCommitRankRequestDto = new TotalCommitRankRequestDto(totalCommitRank, startRank);

        TotalCommitRankResponseDto result = githubCommitService.fetchRankFromStartRank(totalCommitRankRequestDto);
        assertThat(result.getCommitRank().size()).isEqualTo(10);
        checkStartRankAndEndRank(result.getCommitRank(), startRank, endRank);
    }

    @Test
    @DisplayName("전체 랭킹(21위)에서 21위를 가져온다")
    void 전체_랭킹에서_21위를_가져온다() {
        List<UserCommitRankDetailResponseDto> totalCommitRank = generateTotalCommitRank(21);
        int startRank = 21;
        int endRank = 21;
        TotalCommitRankRequestDto totalCommitRankRequestDto = new TotalCommitRankRequestDto(totalCommitRank, startRank);

        TotalCommitRankResponseDto result = githubCommitService.fetchRankFromStartRank(totalCommitRankRequestDto);
        assertThat(result.getCommitRank().size()).isEqualTo(1);
        checkStartRankAndEndRank(result.getCommitRank(), startRank, endRank);
    }

    private void checkStartRankAndEndRank(List<UserCommitRankDetailResponseDto> commitRank, int startRank, int endRank) {
        assertThat(commitRank.get(0).getRank()).isEqualTo(startRank);
        assertThat(commitRank.get(commitRank.size() - 1).getRank()).isEqualTo(endRank);
    }

    private List<UserCommitRankDetailResponseDto> generateTotalCommitRank(int size) {
        List<UserCommitRankDetailResponseDto> totalCommitRank = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            totalCommitRank.add(new UserCommitRankDetailResponseDto(i + 1, 300, 1, "githubId", "nickname"));
        }
        return totalCommitRank;
    }
}