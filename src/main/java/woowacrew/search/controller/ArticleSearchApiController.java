package woowacrew.search.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.search.service.ArticleSearchService;

@RestController
@RequestMapping("/api/articles/search")
public class ArticleSearchApiController {

    private static final String ID = "id";

    private final ArticleSearchService articleSearchService;

    public ArticleSearchApiController(ArticleSearchService articleSearchService) {
        this.articleSearchService = articleSearchService;
    }

    @GetMapping
    public ResponseEntity<ArticleResponseDtos> list(
            @PageableDefault(size = ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE, sort = ID, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String type,
            @RequestParam String content) {
        return ResponseEntity.ok(articleSearchService.findAll(type, content, pageable));
    }
}