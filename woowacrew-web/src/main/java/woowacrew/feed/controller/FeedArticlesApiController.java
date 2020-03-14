package woowacrew.feed.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.dto.FeedArticleResponseDtos;
import woowacrew.feed.service.FeedService;
import woowacrew.search.domain.SearchSpec;
import woowacrew.search.domain.SearchType;
import woowacrew.utils.annotation.AllowedSearchType;

import static woowacrew.article.free.service.ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE;

@RestController
@RequestMapping("/api/feed-articles")
public class FeedArticlesApiController {
    private final FeedService feedService;

    public FeedArticlesApiController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public ResponseEntity<FeedArticleResponseDtos> list
            (@PageableDefault(size = DEFAULT_ARTICLE_PAGE_SIZE, sort = "publishedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(feedService.findAllFeedArticles(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<FeedArticleResponseDtos> search
            (@PageableDefault(size = DEFAULT_ARTICLE_PAGE_SIZE, sort = "publishedDate", direction = Sort.Direction.DESC) Pageable pageable,
             @AllowedSearchType(type = {SearchType.FEED_TITLE, SearchType.FEED_DESCRIPTION}) SearchSpec<FeedArticle> searchSpec) {
        return ResponseEntity.ok(feedService.findSearchedArticles(searchSpec.getSpecification(), pageable));
    }
}
