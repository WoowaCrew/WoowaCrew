package woowacrew.feed.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import woowacrew.feed.dto.FeedArticleResponseDto;
import woowacrew.feed.dto.FeedSourceRequestDto;
import woowacrew.feed.dto.FeedSourceResponseDto;
import woowacrew.feed.dto.FeedSourceUpdateRequestDto;
import woowacrew.feed.service.FeedService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/feeds")
public class FeedApiController {
    private final FeedService feedService;

    public FeedApiController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeedSourceResponseDto> registerFeedSource(@Valid FeedSourceRequestDto feedSourceRequestDto) {
        return ResponseEntity.ok(feedService.registerFeedSource(feedSourceRequestDto));
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FeedArticleResponseDto>> updateFeed() {
        return ResponseEntity.ok(feedService.updateFeed());
    }

    @PutMapping("/{feedSourceId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeedSourceResponseDto> updateFeedSourceDescription(@PathVariable Long feedSourceId, FeedSourceUpdateRequestDto updateRequestDto) {
        return ResponseEntity.ok(feedService.updateFeedSourceDesciption(feedSourceId, updateRequestDto));
    }
}
