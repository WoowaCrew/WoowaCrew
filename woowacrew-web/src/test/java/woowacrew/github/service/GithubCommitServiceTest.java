package woowacrew.github.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.github.dto.GithubCommitRequestDto;
import woowacrew.user.service.UserInternalService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}