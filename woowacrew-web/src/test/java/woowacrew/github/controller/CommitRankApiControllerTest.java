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
import woowacrew.github.dto.TotalCommitRankRequestDto;
import woowacrew.github.dto.TotalCommitRankResponseDto;
import woowacrew.github.dto.UserCommitRankDetailResponseDto;
import woowacrew.github.exception.CommitRankBoundaryException;
import woowacrew.github.exception.NotFoundMyTodayCommitRankException;
import woowacrew.github.service.GithubCommitService;
import woowacrew.security.SecurityContextSupport;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
        UserCommitRankDetailResponseDto myCommitRank = new UserCommitRankDetailResponseDto(1, 300, 1, "hyo", "hyoo");
        when(githubCommitService.getLoginUserCommitRank(userContext)).thenReturn(myCommitRank);

        String result = mockMvc.perform(get("/api/github/commit/rank/me"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        checkContainsData(result, myCommitRank);
    }

    private void checkContainsData(String result, UserCommitRankDetailResponseDto myCommitRank) {
        assertThat(result).contains(myCommitRank.getGithubId())
                .contains(myCommitRank.getNickname())
                .contains(String.valueOf(myCommitRank.getDegree()))
                .contains(String.valueOf(myCommitRank.getPoint()));
    }

    @Test
    void 오늘_유저의_랭킹_정보가_없으면_400에러가_발생한다() throws Exception {
        when(githubCommitService.getLoginUserCommitRank(userContext)).thenThrow(NotFoundMyTodayCommitRankException.class);

        mockMvc.perform(get("/api/github/commit/rank/me"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 정상적으로_1위부터_10위까지_랭킹을_가져온다() throws Exception {
        List<UserCommitRankDetailResponseDto> commitRank = generateTotalCommitRank(10);
        TotalCommitRankResponseDto responseDto = new TotalCommitRankResponseDto(commitRank, true);
        when(githubCommitService.getTotalCommitRank()).thenReturn(commitRank);
        when(githubCommitService.fetchRankFromStartRank(any(TotalCommitRankRequestDto.class))).thenReturn(responseDto);

        String resultContent = mockMvc.perform(
                get("/api/github/commit/rank")
                        .param("startRank", "1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertNotNull(resultContent);
    }

    private List<UserCommitRankDetailResponseDto> generateTotalCommitRank(int size) {
        List<UserCommitRankDetailResponseDto> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(new UserCommitRankDetailResponseDto(i + 1, 300, 1, "githubId", "nickname"));
        }
        return result;
    }

    @Test
    void 커밋_랭킹의_범위가_넘어가면_400에러가_발생한다() throws Exception {
        when(githubCommitService.getTotalCommitRank()).thenReturn(generateTotalCommitRank(10));
        when(githubCommitService.fetchRankFromStartRank(any(TotalCommitRankRequestDto.class))).thenThrow(CommitRankBoundaryException.class);

        mockMvc.perform(
                get("/api/github/commit/rank")
                        .param("startRank", "2"))
                .andExpect(status().isBadRequest());
    }
}