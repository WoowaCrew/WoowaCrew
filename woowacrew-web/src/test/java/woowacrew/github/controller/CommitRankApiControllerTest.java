package woowacrew.github.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import woowacrew.github.dto.UserCommitRankAndPointDto;
import woowacrew.github.dto.UserCommitRankDetailResponseDto;
import woowacrew.github.service.GithubCommitService;
import woowacrew.security.SecurityContextSupport;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommitRankApiController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "woowacrew.security.*")})
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
class CommitRankApiControllerTest {

    @MockBean
    private GithubCommitService githubCommitService;

    @Autowired
    private MockMvc mockMvc;

    private UserContext userContext;

    @BeforeEach
    void setUp() {
        userContext = new UserContext(1L, "12345", "admin", UserRole.ROLE_PRECOURSE);
        SecurityContextSupport.updateContext(userContext);
    }

    @Test
    void 정상적으로_로그인중인_유저의_커밋_랭킹_정보를_가져온다() throws Exception {
        UserCommitRankAndPointDto userCommitRankAndPointDto = new UserCommitRankAndPointDto(1, 300);
        UserCommitRankDetailResponseDto userCommitRankDetailResponseDto = UserCommitRankDetailResponseDto.of(userCommitRankAndPointDto, 1, "hyo", "hyoo");

        when(githubCommitService.getMyCommitRank(userContext)).thenReturn(userCommitRankDetailResponseDto);

        mockMvc.perform(get("/api/github/commit/rank/me"))
                .andExpect(status().isOk());
    }
}