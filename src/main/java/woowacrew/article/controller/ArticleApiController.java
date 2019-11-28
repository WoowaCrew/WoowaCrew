package woowacrew.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.article.domain.ArticleResponseDto;
import woowacrew.article.service.ArticleService;

import java.util.List;

@RestController
public class ArticleApiController {

    private final ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponseDto>> list() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("api/articles/{articleId}")
    public ResponseEntity<ArticleResponseDto> show(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.findById(articleId));
    }
}
