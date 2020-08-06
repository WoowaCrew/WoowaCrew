package woowacrew.github.service;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.github.domain.GithubCommit;
import woowacrew.github.domain.GithubCommitRepository;
import woowacrew.github.dto.GithubCommitStateDto;
import woowacrew.github.dto.UserCommitRankAndPointDto;
import woowacrew.github.exception.GithubCommitCrawlingFailException;
import woowacrew.github.exception.NotFoundCommitRankException;
import woowacrew.github.exception.NotFoundMyTodayCommitRankException;
import woowacrew.github.exception.SaveGithubCommitFailException;
import woowacrew.user.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GithubCommitInternalServiceTest {

    @Mock
    private GithubCommitCrawlingService githubCommitCrawlingService;

    @Mock
    private GithubCommitRepository githubCommitRepository;

    @InjectMocks
    private GithubCommitInternalService githubCommitInternalService;

    private static List<GithubCommitStateDto> createGithubCommitStateDto(LocalDate date) {
        List<GithubCommitStateDto> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            result.add(new GithubCommitStateDto(date, 1));
        }
        return result;
    }

    @Test
    void 정상적으로_커밋_점수를_계산한다() {
        String githubId = "hyojaekim";
        LocalDate date = LocalDate.of(2020, 6, 1);
        List<GithubCommitStateDto> commitState = createGithubCommitStateDto(date);

        when(githubCommitCrawlingService.fetchCommitState(githubId, date)).thenReturn(commitState);

        assertThat(githubCommitInternalService.calculateCommitPoint(githubId, date)).isEqualTo(300);
    }

    @Test
    void 모든_유저의_커밋_점수를_저장한다() {
        LocalDate date = LocalDate.of(2020, 6, 1);
        List<GithubCommitStateDto> commitState = createGithubCommitStateDto(date);
        User mockUser = mock(User.class);
        List<User> users = getMockUsers(mockUser, 4);

        when(mockUser.getGithubId()).thenReturn("githubId");
        when(githubCommitCrawlingService.fetchCommitState(anyString(), any(LocalDate.class))).thenReturn(commitState);

        githubCommitInternalService.save(users, date);

        verify(githubCommitRepository, times(users.size())).save(any(GithubCommit.class));
    }

    private List<User> getMockUsers(User mockUser, int repetitionCount) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < repetitionCount; i++) {
            users.add(mockUser);
        }
        return users;
    }

    @Test
    void 크롤링하는데_실패하는_경우_예외가_발생한다() {
        User mockUser = mock(User.class);
        List<User> users = getMockUsers(mockUser, 5);
        LocalDate date = LocalDate.of(2020, 6, 1);

        when(mockUser.getGithubId()).thenReturn("githubId");
        when(githubCommitCrawlingService.fetchCommitState(anyString(), any(LocalDate.class)))
                .thenThrow(GithubCommitCrawlingFailException.class);

        assertThrows(SaveGithubCommitFailException.class, () -> githubCommitInternalService.save(users, date));

        verify(githubCommitRepository, times(0)).save(any(GithubCommit.class));
        verify(githubCommitCrawlingService, times(1)).fetchCommitState(anyString(), any(LocalDate.class));
    }

    @Test
    void 정상적으로_유저로_커밋_정보를_찾는다() {
        User mockUser = mock(User.class);
        GithubCommit mockGithubCommit = mock(GithubCommit.class);

        when(githubCommitRepository.existsByUserAndDate(any(User.class), any(LocalDate.class))).thenReturn(true);
        when(githubCommitRepository.findByDateOrderByPointDesc(any(LocalDate.class))).thenReturn(Collections.singletonList(mockGithubCommit));
        when(mockGithubCommit.isSameUser(any(User.class))).thenReturn(true);
        when(mockGithubCommit.getPoint()).thenReturn(200);

        UserCommitRankAndPointDto result = githubCommitInternalService.getCommitRankByUser(mockUser);

        assertNotNull(result);
        assertThat(result.getRank()).isEqualTo(1);
        assertThat(result.getPoint()).isEqualTo(200);
    }

    @Test
    void 커밋_정보를_못찾는_경우_예외가_발생한다() {
        User mockUser = mock(User.class);

        when(githubCommitRepository.existsByUserAndDate(any(User.class), any(LocalDate.class))).thenReturn(true);
        when(githubCommitRepository.findByDateOrderByPointDesc(any(LocalDate.class))).thenReturn(Lists.emptyList());

        assertThrows(NotFoundCommitRankException.class, () -> githubCommitInternalService.getCommitRankByUser(mockUser));
    }

    @Test
    void 오늘의_랭킹이_없다면_예외가_발생한다() {
        User mockUser = mock(User.class);

        when(githubCommitRepository.existsByUserAndDate(any(User.class), any(LocalDate.class))).thenReturn(false);

        assertThrows(NotFoundMyTodayCommitRankException.class, () -> githubCommitInternalService.getCommitRankByUser(mockUser));
    }

    @Test
    void 전체_커밋_랭킹을_가져온다() {
        LocalDate date = LocalDate.now();

        when(githubCommitRepository.findTop50ByDateOrderByPointDesc(any(LocalDate.class))).thenReturn(Lists.emptyList());

        githubCommitInternalService.getTotalCommitRank(date);

        verify(githubCommitRepository, times(1)).findTop50ByDateOrderByPointDesc(any(LocalDate.class));
    }
}