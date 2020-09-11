package woowacrew.github.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import woowacrew.github.domain.GithubCommitRanking;
import woowacrew.github.dto.*;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubCommitService {

    private final GithubCommitInternalService githubCommitInternalService;
    private final UserInternalService userInternalService;

    public GithubCommitService(GithubCommitInternalService githubCommitInternalService, UserInternalService userInternalService) {
        this.githubCommitInternalService = githubCommitInternalService;
        this.userInternalService = userInternalService;
    }

    public void save(ThisMonthCommitRankRequestDto requestDto) {
        LocalDate date = getFirstDateByYearAndMonth(requestDto.getYear(), requestDto.getMonth());
        List<User> users = this.userInternalService.findByGithubIdIsNotNull();
        this.githubCommitInternalService.save(users, date);
    }

    private LocalDate getFirstDateByYearAndMonth(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    private LocalDate getFirstDateOfThisMonth() {
        LocalDate date = LocalDate.now();
        return getFirstDateByYearAndMonth(date.getYear(), date.getMonthValue());
    }

    public UserCommitRankDetailResponseDto getLoginUserCommitRank(UserContext userContext) {
        User user = userInternalService.findById(userContext.getId());
        int degree = user.getDegree().getDegreeNumber();
        UserCommitRankAndPointDto userCommitRankAndPointDto = githubCommitInternalService.getCommitRankByUser(user);
        int rank = userCommitRankAndPointDto.getRank();
        int point = userCommitRankAndPointDto.getPoint();
        return new UserCommitRankDetailResponseDto(rank, point, degree, user.getGithubId(), user.getNickname());
    }

    @Cacheable(value = "totalCommitRank")
    public List<UserCommitRankDetailResponseDto> getTotalCommitRank() {
        LocalDate date = getFirstDateOfThisMonth();
        return this.githubCommitInternalService.getTotalCommitRank(date)
                .stream()
                .map(githubCommit -> {
                    User user = githubCommit.getUser();
                    return new UserCommitRankDetailResponseDto(
                            githubCommit.getPoint(),
                            user.getDegree().getDegreeNumber(),
                            user.getGithubId(),
                            user.getNickname());
                })
                .collect(Collectors.toList());
    }

    public TotalCommitRankResponseDto fetchRankFromStartRank(TotalCommitRankRequestDto totalCommitRankRequestDto) {
        List<UserCommitRankDetailResponseDto> totalCommitRank = totalCommitRankRequestDto.getTotalCommitRank();
        int startRank = totalCommitRankRequestDto.getStartRank();
        GithubCommitRanking commitRanking = GithubCommitRanking.of(totalCommitRank, startRank);
        return new TotalCommitRankResponseDto(commitRanking.getRanking(), commitRanking.isMaxRank());
    }
}
