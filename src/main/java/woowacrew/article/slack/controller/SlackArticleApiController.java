package woowacrew.article.slack.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.dto.SlackMessageResponseDtos;
import woowacrew.article.slack.service.SlackMessageService;

@RestController
@RequestMapping("/api/articles/slack")
public class SlackArticleApiController {

    private SlackMessageService slackMessageService;

    public SlackArticleApiController(SlackMessageService slackMessageService) {
        this.slackMessageService = slackMessageService;
    }

    @GetMapping
    public ResponseEntity<SlackMessageResponseDtos> list(@PageableDefault(size = ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        SlackMessageResponseDtos slackMessages = slackMessageService.findAll(pageable);
        return ResponseEntity.ok(slackMessages);
    }

    @GetMapping("/{slackMessageId}")
    public ResponseEntity<SlackMessageResponseDto> show(@PathVariable Long slackMessageId) {
        SlackMessageResponseDto slackMessage = slackMessageService.findById(slackMessageId);
        return ResponseEntity.ok(slackMessage);
    }
}
