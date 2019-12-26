package woowacrew.article.anonymous.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import woowacrew.article.anonymous.domain.AnonymousArticle;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDtos;
import woowacrew.article.anonymous.dto.AnonymousArticleUpdateDto;
import woowacrew.article.anonymous.service.AnonymousArticleInternalService;
import woowacrew.article.anonymous.service.AnonymousArticleService;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.search.domain.SearchSpec;
import woowacrew.search.domain.SearchType;
import woowacrew.user.dto.UserContext;
import woowacrew.utils.annotation.AllowedSearchType;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/articles/anonymous")
public class AnonymousArticleApiController {

    private final AnonymousArticleService anonymousArticleService;

    public AnonymousArticleApiController(AnonymousArticleService anonymousArticleService) {
        this.anonymousArticleService = anonymousArticleService;
    }

    @GetMapping("/approved")
    public ResponseEntity<AnonymousArticleResponseDtos> approvedList(
            @PageableDefault(
                    size = AnonymousArticleInternalService.DEFAULT_ANONYMOUS_ARTICLE_PAGE_SIZE,
                    sort = "id",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        return ResponseEntity.ok(anonymousArticleService.findApprovedAnonymousArticles(pageable));
    }

    @GetMapping("/unapproved")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AnonymousArticleResponseDto>> unapprovedList() {
        return ResponseEntity.ok(anonymousArticleService.findUnapprovedAnonymousArticles());
    }

    @GetMapping("/{anonymousArticleId}")
    public ResponseEntity<AnonymousArticleResponseDto> show(@PathVariable Long anonymousArticleId, UserContext userContext) {
        AnonymousArticleResponseDto anonymousArticleResponseDto = anonymousArticleService.findById(anonymousArticleId);

        if (userContext.getRole().matchAdmin() || anonymousArticleResponseDto.getIsApproved()) {
            return ResponseEntity.ok(anonymousArticleService.findById(anonymousArticleId));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/search")
    public ResponseEntity<AnonymousArticleResponseDtos> search(
            @AllowedSearchType(type = {SearchType.ANONYMOUS_ARTICLE_TITLE, SearchType.ANONYMOUS_ARTICLE_TITLE_WITH_CONTENT}) SearchSpec<AnonymousArticle> searchSpec,
            @PageableDefault(size = ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(anonymousArticleService.findSearchedArticles(searchSpec, pageable));
    }

    @PostMapping
    public ResponseEntity<AnonymousArticleResponseDto> create(AnonymousArticleRequestDto anonymousArticleRequestDto) {
        AnonymousArticleResponseDto anonymousArticleResponseDto =
                anonymousArticleService.save(anonymousArticleRequestDto);
        return ResponseEntity
                .created(URI.create("/api/articles/anonymous/" + anonymousArticleResponseDto.getAnonymousArticleId()))
                .body(anonymousArticleResponseDto);
    }

    @PostMapping("/{anonymousArticleId}/check")
    public ResponseEntity checkPassword(@PathVariable Long anonymousArticleId, String signingKey) {
        anonymousArticleService.checkSigningKey(anonymousArticleId, signingKey);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{anonymousArticleId}")
    public ResponseEntity<AnonymousArticleResponseDto> update(@PathVariable Long anonymousArticleId,
                                                              AnonymousArticleUpdateDto anonymousArticleUpdateDto) {
        return ResponseEntity.ok(anonymousArticleService.update(anonymousArticleId, anonymousArticleUpdateDto));
    }

    @PutMapping("/{anonymousArticleId}/approve")
    public ResponseEntity<AnonymousArticleResponseDto> approve(@PathVariable Long anonymousArticleId) {
        return ResponseEntity.ok(anonymousArticleService.approve(anonymousArticleId));
    }

    @PutMapping("/{anonymousArticleId}/delete")
    public ResponseEntity<AnonymousArticleResponseDto> delete(@PathVariable Long anonymousArticleId, String signingKey) {
        anonymousArticleService.delete(anonymousArticleId, signingKey);
        return ResponseEntity.ok().build();
    }
}