package woowacrew.github.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import woowacrew.github.dto.GithubCommitRequestDto;
import woowacrew.github.dto.UserCommitRankAndPointDto;
import woowacrew.github.dto.UserCommitRankDetailResponseDto;
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

    public void save(GithubCommitRequestDto requestDto) {
        LocalDate date = createDate(requestDto);
        List<User> users = this.userInternalService.findByGithubIdIsNotNull();
        this.githubCommitInternalService.save(users, date);
    }

    private LocalDate createDate(GithubCommitRequestDto requestDto) {
        return createDate(requestDto.getYear(), requestDto.getMonth());
    }

    private LocalDate createDate() {
        LocalDate date = LocalDate.now();
        return createDate(date.getYear(), date.getMonthValue());
    }

    private LocalDate createDate(int year, int month) {
        return LocalDate.of(year, month, 1);
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
        LocalDate date = createDate();
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
}
