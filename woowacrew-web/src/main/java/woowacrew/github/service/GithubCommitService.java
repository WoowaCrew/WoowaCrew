package woowacrew.github.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import woowacrew.github.dto.*;
import woowacrew.github.exception.CommitRankBoundaryException;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GithubCommitService {

    private static final int MIN_RANK = 1;
    private static final int MAX_RANK = 50;
    private static final int MAX_RANK_COUNT = 10;
    private static final Set<Integer> VALID_START_RANK = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(1, 11, 21, 31, 41)));
    private static final Logger log = LoggerFactory.getLogger(GithubCommitService.class);

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

    public TotalCommitRankResponseDto fetchRankFromStartRank(TotalCommitRankRequestDto totalCommitRankRequestDto) {
        List<UserCommitRankDetailResponseDto> totalCommitRank = totalCommitRankRequestDto.getTotalCommitRank();
        int maxRank = totalCommitRank.size();
        int startRank = totalCommitRankRequestDto.getStartRank();
        int endRank = getEndRank(startRank, maxRank);
        List<UserCommitRankDetailResponseDto> commitRank = fetchRankFromStartRank(totalCommitRank, startRank, endRank);
        boolean isMaxRank = (endRank == maxRank);
        return new TotalCommitRankResponseDto(commitRank, isMaxRank);
    }

    private List<UserCommitRankDetailResponseDto> fetchRankFromStartRank(List<UserCommitRankDetailResponseDto> totalCommitRank, int startRank, int endRank) {
        try {
            int startRankIndex = startRank - 1;
            return totalCommitRank.subList(startRankIndex, endRank);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CommitRankBoundaryException();
        }
    }

    private int getEndRank(int startRank, int maxRank) {
        checkMaxRank(maxRank);
        checkStartRank(startRank, maxRank);
        int startRankIndex = startRank - 1;
        int endRank = startRankIndex + MAX_RANK_COUNT;
        if (endRank > maxRank) {
            endRank = maxRank;
        }
        return endRank;
    }

    private void checkMaxRank(int maxRank) {
        if (maxRank < MIN_RANK || maxRank > MAX_RANK) {
            throw new CommitRankBoundaryException();
        }
    }

    private void checkStartRank(int startRank, int maxRank) {
        if (!VALID_START_RANK.contains(startRank) || startRank > maxRank) {
            throw new CommitRankBoundaryException();
        }
    }
}
