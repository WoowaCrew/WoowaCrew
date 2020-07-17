package woowacrew.github.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.github.domain.GithubCommitRepository;
import woowacrew.github.dto.GithubCommitStateDto;
import woowacrew.github.exception.GithubCommitCrawlingFailException;
import woowacrew.user.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        when(githubCommitCrawlingService.fetchCommitState(anyString(), any())).thenReturn(commitState);

        githubCommitInternalService.save(users, date);

        verify(githubCommitRepository, times(users.size())).save(any());
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
        when(githubCommitCrawlingService.fetchCommitState(anyString(), any()))
                .thenThrow(GithubCommitCrawlingFailException.class);

        assertThrows(GithubCommitCrawlingFailException.class, () -> githubCommitInternalService.save(users, date));

        verify(githubCommitRepository, times(0)).save(any());
        verify(githubCommitCrawlingService, times(1)).fetchCommitState(anyString(), any());
    }
}