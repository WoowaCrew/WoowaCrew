package woowacrew.article.slack.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.service.SlackMessageService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/slack")
public class SlackMessageApiController {
    private static final Logger logger = LoggerFactory.getLogger(SlackMessageApiController.class);

    private SlackMessageService slackMessageService;

    public SlackMessageApiController(SlackMessageService slackMessageService) {
        this.slackMessageService = slackMessageService;
    }

    @GetMapping("/notice")
    public ResponseEntity<SlackMessageResponseDto> notice() {
        SlackMessageResponseDto slackMessage = slackMessageService.findRecentlyNoticeMessage();
        return ResponseEntity.ok(slackMessage);
    }

    @PostMapping
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

    @PostMapping("/birthday-message")
    public ResponseEntity<Void> sendBirthdayMessage(@RequestBody String today) {
        slackMessageService.sendBirthdayMessage(LocalDate.parse(today));
        return ResponseEntity.ok().build();
    }
}
