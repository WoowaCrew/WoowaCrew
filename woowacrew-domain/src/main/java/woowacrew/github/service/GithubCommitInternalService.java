package woowacrew.github.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.github.domain.GithubCommit;
import woowacrew.github.domain.GithubCommitRepository;
import woowacrew.github.dto.GithubCommitStateDto;
import woowacrew.github.dto.UserCommitRankAndPointDto;
import woowacrew.github.utils.GithubCommitCalculator;
import woowacrew.user.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GithubCommitInternalService {

    private final GithubCommitCrawlingService githubCommitCrawlingService;
    private final GithubCommitRepository githubCommitRepository;

    public GithubCommitInternalService(GithubCommitCrawlingService githubCommitCrawlingService, GithubCommitRepository githubCommitRepository) {
        this.githubCommitCrawlingService = githubCommitCrawlingService;
        this.githubCommitRepository = githubCommitRepository;
    }

    @Transactional
    public void save(List<User> users, LocalDate date) {
        for (User user : users) {
            int point = calculateCommitPoint(user.getGithubId(), date);
            this.githubCommitRepository.save(new GithubCommit(user, date, point));
        }
    }

    public int calculateCommitPoint(String githubId, LocalDate date) {
        List<GithubCommitStateDto> commitState = githubCommitCrawlingService.fetchCommitState(githubId, date);
        return GithubCommitCalculator.calculate(commitState);
    }

    @Transactional(readOnly = true)
    public UserCommitRankAndPointDto getCommitRankByUser(User user) {
        AtomicInteger rank = new AtomicInteger();
        return this.githubCommitRepository.findByOrderByPointDesc()
                .stream()
                .filter(githubCommit -> {
                    rank.set(rank.get() + 1);
                    return githubCommit.isSameUser(user);
                })
                .findAny()
                .map(githubCommit -> new UserCommitRankAndPointDto(rank.get(), githubCommit.getPoint()))
                .orElseThrow(RuntimeException::new);
    }
}
