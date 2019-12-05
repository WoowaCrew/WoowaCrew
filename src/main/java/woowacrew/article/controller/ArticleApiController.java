package woowacrew.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacrew.article.domain.ArticleRequestDto;
import woowacrew.article.domain.ArticleResponseDto;
import woowacrew.article.domain.ArticleUpdateDto;
import woowacrew.article.service.ArticleService;
import woowacrew.user.domain.UserContext;
import woowacrew.utils.annotation.AuthenticationUser;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/articles")
@RestController
public class ArticleApiController {
    public static final String ARTICLES_URL = "/articles/";

    private final ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public ResponseEntity<List<ArticleResponseDto>> list() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> show(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.findById(articleId));
    }

    @PostMapping()
    public ResponseEntity<ArticleResponseDto> createArticle(@AuthenticationUser UserContext userContext, ArticleRequestDto articleRequestDto) {
        ArticleResponseDto articleResponseDto = articleService.save(articleRequestDto, userContext);
        return ResponseEntity.created(URI.create(ARTICLES_URL + articleResponseDto.getId())).body(articleResponseDto);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@AuthenticationUser UserContext userContext, ArticleUpdateDto articleUpdateDto) {
        return ResponseEntity.ok(articleService.update(articleUpdateDto, userContext));
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteArticle(@AuthenticationUser UserContext userContext, @PathVariable Long articleId) {
        articleService.delete(articleId, userContext);
        return ResponseEntity.ok().build();
    }
}
