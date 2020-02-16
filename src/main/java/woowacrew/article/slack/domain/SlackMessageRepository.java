package woowacrew.article.slack.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlackMessageRepository extends JpaRepository<SlackMessage, Long> {
    Optional<SlackMessage> findFirstByOrderByCreatedDateDesc();

    Optional<SlackMessage> findFirstByChannelOrderByCreatedDateDesc(String channel);
}
