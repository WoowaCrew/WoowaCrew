package woowacrew.github.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import woowacrew.github.dto.GithubCommitRequestDto;

import java.time.LocalDate;

@Service
public class GithubCommitSchedulerService {

    private final GithubCommitService githubCommitService;

    public GithubCommitSchedulerService(GithubCommitService githubCommitService) {
        this.githubCommitService = githubCommitService;
    }

    @Scheduled(cron = "0 0 9 * * *")
    @CacheEvict(value = "totalCommitRank")
    public void save() {
        LocalDate now = LocalDate.now();
        GithubCommitRequestDto requestDto = new GithubCommitRequestDto(now.getYear(), now.getMonthValue());
        this.githubCommitService.save(requestDto);
    }
}
