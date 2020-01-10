package woowacrew.article.slack.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.article.slack.dto.SlackMessageResponseDtos;
import woowacrew.article.slack.service.SlackMessageService;

@RestController
public class SlackArticleApiController {

    private SlackMessageService slackMessageService;

    public SlackArticleApiController(SlackMessageService slackMessageService) {
        this.slackMessageService = slackMessageService;
    }

    @GetMapping("/api/articles/slack")
    public SlackMessageResponseDtos list(@PageableDefault(size = ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return slackMessageService.findAll(pageable);
    }
}
