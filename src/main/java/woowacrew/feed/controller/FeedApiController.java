package woowacrew.feed.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.feed.dto.FeedArticleResponseDto;
import woowacrew.feed.dto.FeedArticleResponseDtos;
import woowacrew.feed.dto.FeedRegisterDto;
import woowacrew.feed.service.FeedService;

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
    public ResponseEntity<FeedArticleResponseDtos> findAllFeedArticles
            (@PageableDefault(size = DEFAULT_ARTICLE_PAGE_SIZE, sort = "publishedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(feedService.findAllFeedArticles(pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeedRegisterDto> registerFeedSource(FeedRegisterDto feedRegisterDto) {
        return ResponseEntity.ok(feedService.registerFeedSource(feedRegisterDto));
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FeedArticleResponseDto>> updateFeed() {
        return ResponseEntity.ok(feedService.updateFeed());
    }
}
