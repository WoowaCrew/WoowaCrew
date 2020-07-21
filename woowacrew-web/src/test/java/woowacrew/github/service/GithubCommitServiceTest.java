package woowacrew.github.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.degree.domain.Degree;
import woowacrew.github.dto.GithubCommitRequestDto;
import woowacrew.github.dto.UserCommitRankAndPointDto;
import woowacrew.github.dto.UserCommitRankDetailResponseDto;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

import java.time.LocalDate;

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
        githubCommitService.save(new GithubCommitRequestDto(2020, 6));

        verify(userInternalService, times(1)).findByGithubIdIsNotNull();
        verify(githubCommitInternalService, times(1)).save(any(), any());
    }

    @Test
    void 정상적으로_나의_커밋_랭킹_정보를_가져온다() {
        User user = new User("oauthId", "githubId", new Degree());
        user.updateUserInfo("hyo", LocalDate.of(1995, 6, 8));

        UserContext userContext = new UserContext(1L, user.getOauthId(), user.getNickname(), UserRole.ROLE_PRECOURSE);

        when(userInternalService.findById(any())).thenReturn(user);
        when(githubCommitInternalService.getCommitRankByUser(user)).thenReturn(new UserCommitRankAndPointDto(1, 200));

        UserCommitRankDetailResponseDto result = githubCommitService.getMyCommitRank(userContext);

        assertNotNull(result);
        assertThat(result.getRank()).isEqualTo(1);
        assertThat(result.getDegree()).isEqualTo(0);
        assertThat(result.getPoint()).isEqualTo(200);
        assertThat(result.getGithubId()).isEqualTo("githubId");
        assertThat(result.getNickname()).isEqualTo("hyo");
    }
}