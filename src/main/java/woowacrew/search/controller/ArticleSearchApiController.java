package woowacrew.search.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.search.service.ArticleSearchService;

@Controller
@RequestMapping("/api/search/articles")
public class ArticleSearchApiController {

    private static final String CREATED_DATE = "createdDate";

    private final ArticleSearchService articleSearchService;

    public ArticleSearchApiController(ArticleSearchService articleSearchService) {
        this.articleSearchService = articleSearchService;
    }

    @PostMapping("/title")
    public ResponseEntity<ArticleResponseDtos> list(
            String content,
            @PageableDefault(size = ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE, sort = CREATED_DATE, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(articleSearchService.findAllByTitle(content, pageable));
    }
}
