package woowacrew.github.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import woowacrew.github.dto.GithubCommitStateDto;
import woowacrew.github.exception.DateRangeException;
import woowacrew.github.exception.GithubCommitCrawlingFailException;
import woowacrew.github.utils.GithubCommitStateConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubCommitCrawlingService {

    private static final Logger log = LoggerFactory.getLogger(GithubCommitCrawlingService.class);
    private static final String GITHUB_DOMAIN = "https://github.com/";

    public List<GithubCommitStateDto> fetchCommitState(String githubId, LocalDate currentDate) {
        try {
            Elements fullCommitState = this.fetchFullCommitState(githubId);
            this.checkContainsDate(fullCommitState, currentDate);
            return this.getCommitState(fullCommitState, currentDate.getYear(), currentDate.getMonthValue());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GithubCommitCrawlingFailException();
        }
    }

    private Elements fetchFullCommitState(String githubId) throws IOException {
        String url = GITHUB_DOMAIN + githubId;
        Document document = Jsoup.connect(url).get();
        return document.getElementsByTag("g").get(0).getElementsByClass("day");
    }

    private void checkContainsDate(Elements fullCommitState, LocalDate currentDate) {
        int firstIndex = 0;
        int lastIndex = fullCommitState.size() - 1;

        LocalDate startDate = getDate(fullCommitState, firstIndex);
        LocalDate endDate = getDate(fullCommitState, lastIndex);
        if (!isContainsDate(currentDate, startDate, endDate)) {
            throw new DateRangeException();
        }
    }

    private LocalDate getDate(Elements fullCommitState, int index) {
        return LocalDate.parse(fullCommitState.get(index).attr("data-date"));
    }

    private boolean isContainsDate(LocalDate currentDate, LocalDate startDate, LocalDate endDate) {
        if (currentDate.isEqual(startDate) || currentDate.isEqual(endDate)) {
            return true;
        }
        return currentDate.isAfter(startDate) && currentDate.isBefore(endDate);
    }

    private List<GithubCommitStateDto> getCommitState(Elements fullDate, int currentYear, int currentMonth) {
        return fullDate.stream()
                .map(GithubCommitStateConverter::toDto)
                .filter(data -> isSameYearAndMonth(data.getDate(), currentYear, currentMonth))
                .sorted(Comparator.comparing(GithubCommitStateDto::getDate))
                .collect(Collectors.toList());
    }

    private boolean isSameYearAndMonth(LocalDate date, int currentYear, int currentMonth) {
        return date.getYear() == currentYear && date.getMonthValue() == currentMonth;
    }
}

