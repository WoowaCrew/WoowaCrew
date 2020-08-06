package woowacrew.github.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.github.domain.GithubCommit;
import woowacrew.github.domain.GithubCommitRepository;
import woowacrew.github.dto.GithubCommitStateDto;
import woowacrew.github.dto.UserCommitRankAndPointDto;
import woowacrew.github.exception.NotFoundCommitRankException;
import woowacrew.github.exception.NotFoundMyTodayCommitRankException;
import woowacrew.github.exception.SaveGithubCommitFailException;
import woowacrew.github.utils.DateConverter;
import woowacrew.github.utils.GithubCommitCalculator;
import woowacrew.user.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class GithubCommitInternalService {

    private static final Logger log = LoggerFactory.getLogger(GithubCommitInternalService.class);

    private final GithubCommitCrawlingService githubCommitCrawlingService;
    private final GithubCommitRepository githubCommitRepository;

    public GithubCommitInternalService(GithubCommitCrawlingService githubCommitCrawlingService, GithubCommitRepository githubCommitRepository) {
        this.githubCommitCrawlingService = githubCommitCrawlingService;
        this.githubCommitRepository = githubCommitRepository;
    }

    public void save(List<User> users, LocalDate date) {
        for (User user : users) {
            saveGithubCommit(user, date);
        }
    }

    private void saveGithubCommit(User user, LocalDate date) {
        try {
            int point = calculateCommitPoint(user.getGithubId(), date);
            this.githubCommitRepository.save(new GithubCommit(user, date, point));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SaveGithubCommitFailException();
        }
    }

    public int calculateCommitPoint(String githubId, LocalDate date) {
        List<GithubCommitStateDto> commitState = githubCommitCrawlingService.fetchCommitState(githubId, date);
        return GithubCommitCalculator.calculate(commitState);
    }

    public UserCommitRankAndPointDto getCommitRankByUser(User user) {
        LocalDate date = DateConverter.toFirstDay(LocalDate.now());
        if (!githubCommitRepository.existsByUserAndDate(user, date)) {
            throw new NotFoundMyTodayCommitRankException();
        }
        return findMyCommitRank(user, date);
    }

    private UserCommitRankAndPointDto findMyCommitRank(User user, LocalDate date) {
        AtomicInteger rank = new AtomicInteger();
        return this.githubCommitRepository.findByDateOrderByPointDesc(date)
                .stream()
                .filter(githubCommit -> {
                    rank.getAndIncrement();
                    return githubCommit.isSameUser(user);
                })
                .findFirst()
                .map(githubCommit -> new UserCommitRankAndPointDto(rank.get(), githubCommit.getPoint()))
                .orElseThrow(NotFoundCommitRankException::new);
    }

    @Transactional(readOnly = true)
    public List<GithubCommit> getTotalCommitRank(LocalDate date) {
        return githubCommitRepository.findTop50ByDateOrderByPointDesc(date);
    }
}
