package woowacrew.github.utils;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import woowacrew.github.dto.GithubCommitStateDto;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class GithubCommitStateConverterTest {

    private Element element;

    @BeforeEach
    void setUp() {
        Attributes attributes = new Attributes();
        attributes.put("data-date", "2019-07-07");
        attributes.put("data-count", "3");

        Tag tag = Tag.valueOf("rect");
        this.element = new Element(tag, "https://github.com/hyojaekim", attributes);
    }

    @Test
    void 정상적으로_DTO로_변환한다() {
        GithubCommitStateDto result = GithubCommitStateConverter.toDto(element);

        assertThat(result.getCommitCount()).isEqualTo(3);
        assertThat(result.getDate()).isEqualTo(LocalDate.of(2019, 7, 7));
    }
}