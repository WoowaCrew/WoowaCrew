package woowacrew.feed.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import woowacrew.feed.dto.*;
import woowacrew.feed.service.FeedService;

import javax.validation.Valid;
import java.util.List;

import static woowacrew.article.free.service.ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE;

@RestController
@RequestMapping("/api/feeds")
public class FeedApiController {
    private final FeedService feedService;

    public FeedApiController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public ResponseEntity<FeedArticleResponseDtos> list
            (@PageableDefault(size = DEFAULT_ARTICLE_PAGE_SIZE, sort = "publishedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(feedService.findAllFeedArticles(pageable));
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
