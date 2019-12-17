package woowacrew.feed.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.feed.dto.FeedRegisterDto;
import woowacrew.feed.service.FeedService;

@RestController
@RequestMapping("/api/feeds")
public class FeedApiController {
    private final FeedService feedService;

    public FeedApiController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeedRegisterDto> registerFeedSource(FeedRegisterDto feedRegisterDto) {
        return ResponseEntity.ok(feedService.registerFeedSource(feedRegisterDto));
    }
}
