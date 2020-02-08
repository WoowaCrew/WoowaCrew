package woowacrew.article.slack.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.service.SlackMessageService;

@RestController
public class SlackMessageApiController {
    private static final Logger logger = LoggerFactory.getLogger(SlackMessageApiController.class);

    private SlackMessageService slackMessageService;

    public SlackMessageApiController(SlackMessageService slackMessageService) {
        this.slackMessageService = slackMessageService;
    }

    @GetMapping("/api/slack/notice")
    public ResponseEntity<SlackMessageResponseDto> notice() {
        SlackMessageResponseDto slackMessage = slackMessageService.findRecentlyMessage();
        return ResponseEntity.ok(slackMessage);
    }

    @PostMapping("/api/slack")
    public ResponseEntity<String> save(SlackMessageRequestDto slackMessageRequestDto) {
        if (slackMessageRequestDto.getChallenge() != null) {
            return ResponseEntity.ok(slackMessageRequestDto.getChallenge());
        }
        if (slackMessageRequestDto.isBot()) {
            return ResponseEntity.badRequest().build();
        }
        SlackMessageResponseDto savedSlackMessage = slackMessageService.save(slackMessageRequestDto);
        logger.debug("Successful saved of slack message : {}", savedSlackMessage.toString());
        return ResponseEntity.ok().build();
    }
}
