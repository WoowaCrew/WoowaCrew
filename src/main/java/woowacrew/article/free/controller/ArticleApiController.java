package woowacrew.article.free.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.dto.ArticleUpdateDto;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.article.free.service.ArticleService;
import woowacrew.search.domain.SearchSpec;
import woowacrew.search.domain.SearchType;
import woowacrew.user.dto.UserContext;
import woowacrew.utils.annotation.AllowedSearchType;

import java.net.URI;

@RequestMapping("/api/articles")
@RestController
public class ArticleApiController {
    public static final String ARTICLES_URL = "/articles/";

    private final ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public ResponseEntity<ArticleResponseDtos> list
            (@PageableDefault(size = ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(articleService.findAll(pageable));
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> show(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.findById(articleId));
    }

    @GetMapping("/search")
    public ResponseEntity<ArticleResponseDtos> search(
            @AllowedSearchType(type = {SearchType.TITLE, SearchType.TITLE_WITH_CONTENT, SearchType.AUTHOR}) SearchSpec<Article> searchSpec,
            @PageableDefault(size = ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(articleService.findSearchedArticles(searchSpec.getSpecification(), pageable));
    }

    @PostMapping()
    public ResponseEntity<ArticleResponseDto> createArticle(UserContext userContext, ArticleRequestDto articleRequestDto) {
        ArticleResponseDto articleResponseDto = articleService.save(articleRequestDto, userContext);
        return ResponseEntity.created(URI.create(ARTICLES_URL + articleResponseDto.getId())).body(articleResponseDto);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> updateArticle(UserContext userContext, ArticleUpdateDto articleUpdateDto) {
        return ResponseEntity.ok(articleService.update(articleUpdateDto, userContext));
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteArticle(UserContext userContext, @PathVariable Long articleId) {
        articleService.delete(articleId, userContext);
        return ResponseEntity.ok().build();
    }
}
