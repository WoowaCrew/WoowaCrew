package woowacrew.article.slack.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SlackMessageRepositoryTest {
    @Autowired
    private SlackMessageRepository slackMessageRepository;

    @Test
    void 정상적으로_공지사항을_가져오는지_확인한다() {
        SlackMessage actualMessage =
                slackMessageRepository.findFirstByChannelOrderByCreatedDateDesc("전체-공지사항")
                        .orElseThrow(IllegalArgumentException::new);

        assertThat(actualMessage.getChannel()).isEqualTo("전체-공지사항");
    }
}