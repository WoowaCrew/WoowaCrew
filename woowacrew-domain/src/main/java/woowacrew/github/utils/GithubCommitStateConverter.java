package woowacrew.github.utils;

import org.jsoup.nodes.Element;
import woowacrew.github.dto.GithubCommitStateDto;

import java.time.LocalDate;

public class GithubCommitStateConverter {

    private static final String DATE = "data-date";
    private static final String COMMIT_COUNT = "data-count";

    private GithubCommitStateConverter() {
    }

    public static GithubCommitStateDto toDto(Element element) {
        LocalDate date = LocalDate.parse(element.attr(DATE));
        int commitCount = Integer.parseInt(element.attr(COMMIT_COUNT));
        return new GithubCommitStateDto(date, commitCount);
    }
}
