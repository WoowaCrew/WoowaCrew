package woowacrew.article.crew.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacrew.article.crew.domain.CrewArticle;
import woowacrew.article.crew.service.CrewArticleService;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.dto.ArticleUpdateDto;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.search.domain.SearchSpec;
import woowacrew.search.domain.SearchType;
import woowacrew.user.dto.UserContext;
import woowacrew.utils.annotation.AllowedSearchType;

import java.net.URI;

@RequestMapping("/api/articles/crew")
@RestController
public class CrewArticleApiController {
    private static final String CREW_ARTICLE_URL = "/articles/crew/";

    private final CrewArticleService crewArticleService;

    public CrewArticleApiController(CrewArticleService crewArticleService) {
        this.crewArticleService = crewArticleService;
    }

    @GetMapping
    public ResponseEntity<ArticleResponseDtos> list
            (@PageableDefault(size = ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
             UserContext userContext) {
        return ResponseEntity.ok(crewArticleService.findAllByCrew(userContext, pageable));
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> show(@PathVariable Long articleId, UserContext userContext) {
        return ResponseEntity.ok(crewArticleService.findById(articleId, userContext));
    }

    @GetMapping("/search")
    public ResponseEntity<ArticleResponseDtos> search(
            @AllowedSearchType(type = {SearchType.TITLE, SearchType.TITLE_WITH_CONTENT, SearchType.AUTHOR}) SearchSpec<CrewArticle> searchSpec,
            @PageableDefault(size = ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            UserContext userContext) {
        return ResponseEntity.ok(crewArticleService.findSearchedArticles(searchSpec, pageable, userContext));
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(UserContext userContext, ArticleRequestDto articleRequestDto) {
        ArticleResponseDto articleResponseDto = crewArticleService.save(articleRequestDto, userContext);
        return ResponseEntity.created(URI.create(CREW_ARTICLE_URL + articleResponseDto.getId())).body(articleResponseDto);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> updateArticle(UserContext userContext, ArticleUpdateDto articleUpdateDto) {
        return ResponseEntity.ok(crewArticleService.update(articleUpdateDto, userContext));
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteArticle(UserContext userContext, @PathVariable Long articleId) {
        crewArticleService.delete(articleId, userContext);
        return ResponseEntity.ok().build();
    }
}
