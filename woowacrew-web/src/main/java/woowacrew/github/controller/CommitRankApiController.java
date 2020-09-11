package woowacrew.github.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.github.dto.TotalCommitRankRequestDto;
import woowacrew.github.dto.TotalCommitRankResponseDto;
import woowacrew.github.dto.UserCommitRankDetailResponseDto;
import woowacrew.github.service.GithubCommitService;
import woowacrew.user.dto.UserContext;

import java.util.List;

@RestController
@RequestMapping("/api/github/commit/rank")
public class CommitRankApiController {

    private final GithubCommitService githubCommitService;

    public CommitRankApiController(GithubCommitService githubCommitService) {
        this.githubCommitService = githubCommitService;
    }

    @GetMapping
    public ResponseEntity<TotalCommitRankResponseDto> getTotalCommitRank(@RequestParam int startRank) {
        List<UserCommitRankDetailResponseDto> totalCommitRank = this.githubCommitService.getTotalCommitRank();
        TotalCommitRankRequestDto totalCommitRankRequestDto = new TotalCommitRankRequestDto(totalCommitRank, startRank);

        TotalCommitRankResponseDto result = this.githubCommitService.fetchRankFromStartRank(totalCommitRankRequestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/me")
    public ResponseEntity<UserCommitRankDetailResponseDto> getMyCommitRank(UserContext userContext) {
        UserCommitRankDetailResponseDto result = this.githubCommitService.getLoginUserCommitRank(userContext);
        return ResponseEntity.ok(result);
    }
}
