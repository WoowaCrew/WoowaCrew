package woowacrew.search.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.search.service.ArticleSearchService;

@Controller
@RequestMapping("/api/articles/search")
public class ArticleSearchApiController {

    private static final String ID = "id";

    private final ArticleSearchService articleSearchService;

    public ArticleSearchApiController(ArticleSearchService articleSearchService) {
        this.articleSearchService = articleSearchService;
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDtos> list(
            @RequestParam String type,
            @PageableDefault(size = ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE, sort = ID, direction = Sort.Direction.DESC) Pageable pageable,
            String content) {
        return ResponseEntity.ok(articleSearchService.findAll(type, content, pageable));
    }
}