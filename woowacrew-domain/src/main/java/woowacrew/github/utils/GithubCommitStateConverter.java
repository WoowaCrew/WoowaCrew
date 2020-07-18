package woowacrew.github.utils;

import org.jsoup.nodes.Element;
import woowacrew.github.dto.GithubCommitStateDto;

import java.time.LocalDate;

public class GithubCommitStateConverter {

    private GithubCommitStateConverter() {
    }

    public static GithubCommitStateDto toDto(Element element) {
        LocalDate date = LocalDate.parse(element.attr("data-date"));
        int commitCount = Integer.parseInt(element.attr("data-count"));
        return new GithubCommitStateDto(date, commitCount);
    }
}
